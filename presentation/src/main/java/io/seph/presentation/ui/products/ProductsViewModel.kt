package io.seph.presentation.ui.products

import androidx.lifecycle.viewModelScope
import io.seph.domain.model.OrderTypeModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.usecases.FetchProductsWithReviewsUseCase
import io.seph.domain.usecases.OrderReviewUseCase
import io.seph.domain.usecases.SearchProductByNameUseCase
import io.seph.presentation.base.BaseViewModel
import io.seph.presentation.mapper.UiMapper
import io.seph.presentation.models.DataSource
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.ProductsWithReviews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ProductsViewModel(
    private val fetchProductWithReviewsUseCase: FetchProductsWithReviewsUseCase,
    private val orderReviewUseCase: OrderReviewUseCase,
    private val searchProductByNameUseCase: SearchProductByNameUseCase,
    private val productsMapper: UiMapper<ProductsWithReviewModel, ProductsWithReviews>
) : BaseViewModel() {

    data class ProductsState(
        val products: List<ProductWithReviews> = emptyList<ProductWithReviews>(),
        val isLoading: Boolean = false,
        val isInError: Boolean = false,
        val isOnLocalCache: Boolean = false,
    ) : ViewModelState

    private val productsWithReviewsState = MutableStateFlow<List<ProductWithReviews>>(emptyList())
    private val isLoading = MutableStateFlow(false)
    private val isOnLocalCache = MutableStateFlow(false)
    private val isInError = MutableStateFlow(false)

    /**
     *  The current state of the products screen.
     *
     *  It's a combination of loading status, errors, and the actual product data.
     *  When you start listening to this state, it automatically tries to fetch fresh data.
     *  @see BaseViewModel
     */
    override val state: StateFlow<ProductsState>
        get() = combine(
            productsWithReviewsState,
            isLoading,
            isInError,
            isOnLocalCache,
            ::ProductsState
        ).onStart { fetchProducts() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProductsState())

    /**
     * Fetches products and their reviews.
     *
     * This function gets product data, handles loading, errors, and whether the data came from the local data base or the server.
     *
     * @param isRefresh This is not currently used, but could be used to force a refresh from the server in the future.
     *
     * @see FetchProductsWithReviewsUseCase
     */
    fun fetchProducts(isRefresh: Boolean = false) {
        viewModelScope.launch {
            isLoading.update { true }
            isInError.update { false }
            isOnLocalCache.update { false }
            fetchProductWithReviewsUseCase()
                .mapCatching(productsMapper::toUiModel)
                .onSuccess { productsWithReviews ->
                    isLoading.update { false }
                    productsWithReviewsState.update { productsWithReviews.productWithReviews }
                    isOnLocalCache.update { productsWithReviews.dataSourceModel == DataSource.LOCAL }
                }
                .onFailure {
                    isLoading.update { false }
                    isInError.update { true }
                }
        }
    }

    /**
     * Searches for products by name or brand name.
     *
     * Uses the `searchProductByNameUseCase` to find products matching the given name.
     * If successful, it updates the UI with the results. Otherwise, it sets an error flag.
     *
     * @param name The product name to search for.
     *
     * @see SearchProductByNameUseCase
     */
    fun searchProductsByName(name: String) {
        searchProductByNameUseCase.execute(SearchProductByNameUseCase.Params(name))
            .map(productsMapper::toUiModel)
            .onSuccess { productsWithReviews ->
                productsWithReviewsState.update { productsWithReviews.productWithReviews }
            }
            .onFailure { isInError.update { true } }
    }

    /**
     * Reorders product reviews by the given [reorderType].
     *
     * @param reorderType  The desired sort order for the reviews (e.g., [OrderTypeModel.BEST_TO_WORST]).
     *
     * @see OrderReviewUseCase
     */
    fun reorderReview(reorderType: OrderTypeModel) {
        orderReviewUseCase.execute(OrderReviewUseCase.Params(reorderType))
            .map(productsMapper::toUiModel)
            .onSuccess { productsWithReviews ->
                productsWithReviewsState.update { productsWithReviews.productWithReviews }
            }.onFailure { isInError.update { true } }
    }
}