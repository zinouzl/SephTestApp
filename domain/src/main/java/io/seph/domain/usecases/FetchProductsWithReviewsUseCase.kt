package io.seph.domain.usecases

import io.seph.domain.common.NoParamsSuspendingUseCase
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.repositories.ProductsRepository
import io.seph.domain.repositories.ProductsReviewsRepository
import kotlinx.coroutines.CoroutineDispatcher

/**
 * A use case responsible for fetching products along with their reviews.
 *
 * This class retrieves product data from the [ProductsRepository] and review data from the
 * [ProductsReviewsRepository]. It combines these two data sources into a single
 * [ProductsWithReviewModel] for consumption by the UI or other parts of the application.
 *
 * This use case operates on a background thread as defined by the provided [CoroutineDispatcher].
 *
 * @property dispatcher The coroutine dispatcher used to execute the operations.
 * @property productsRepository The repository for fetching product data.
 * @property reviewsRepository The repository for fetching reviews data.
 */
class FetchProductsWithReviewsUseCase(
    override val dispatcher: CoroutineDispatcher,
    private val productsRepository: ProductsRepository,
    private val reviewsRepository: ProductsReviewsRepository
) : NoParamsSuspendingUseCase<ProductsWithReviewModel>() {

    override suspend fun run(): Result<ProductsWithReviewModel> = runCatching {
        return@runCatching ProductsWithReviewModel(
            productsRepository.fetchProducts(),
            reviewsRepository.fetchProductsReviews()
        )
    }
}

