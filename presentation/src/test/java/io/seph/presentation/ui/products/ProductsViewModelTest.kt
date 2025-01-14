package io.seph.presentation.ui.products

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.seph.domain.model.BrandModel
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.model.ReviewModel
import io.seph.domain.usecases.FetchProductsWithReviewsUseCase
import io.seph.domain.usecases.OrderReviewUseCase
import io.seph.domain.usecases.SearchProductByNameUseCase
import io.seph.presentation.mapper.UiMapper
import io.seph.presentation.models.Brand
import io.seph.presentation.models.DataSource
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.ProductsWithReviews
import io.seph.presentation.models.Review
import io.seph.presentation.ui.products.ProductsViewModel.ProductsState
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

private const val PRODUCT_ID = 7381L
private const val PRODUCT_NAME = "Alexandra Henry"
private const val PRODUCT_DESCRIPTION = "description"
private const val PRODUCT_PRICE = 392f
private const val PRODUCT_IMAGE = "image"
private const val REVIEW_NAME = "name"
private const val REVIEW_TEXT = "text"
private const val REVIEW_RATING_1 = 5f
private const val BRAND_ID = "brand_id"
private const val BRAND_NAME = "brand_name"

private val productsWithReviewModel = ProductsWithReviewModel(
    productWithReviews = listOf(
        ProductWithReviewsModel(
            productId = PRODUCT_ID,
            productName = PRODUCT_NAME,
            description = PRODUCT_DESCRIPTION,
            productPrice = PRODUCT_PRICE,
            productImage = PRODUCT_IMAGE,
            brand = BrandModel(
                id = BRAND_ID,
                name = BRAND_NAME
            ),
            reviews = listOf(
                ReviewModel(
                    reviewerName = REVIEW_NAME,
                    reviewText = REVIEW_TEXT,
                    reviewRating = REVIEW_RATING_1
                )
            ),
            isProductSet = false,
            isSpecialBrand = false
        )
    ),
    dataSourceModel = DataSourceModel.REMOTE
)

private val productsWithReviews = ProductsWithReviews(
    productWithReviews = listOf(
        ProductWithReviews(
            productId = PRODUCT_ID,
            productName = PRODUCT_NAME,
            description = PRODUCT_DESCRIPTION,
            productPrice = PRODUCT_PRICE,
            productImage = PRODUCT_IMAGE,
            brand = Brand(
                id = BRAND_ID,
                name = BRAND_NAME
            ),
            reviews = listOf(
                Review(
                    reviewerName = REVIEW_NAME,
                    reviewText = REVIEW_TEXT,
                    reviewRating = REVIEW_RATING_1
                )
            ),
            expended = false

        )
    ),
    dataSourceModel = DataSource.REMOTE

)

class ProductsViewModelTest : BaseUnitTest() {

    // class under test
    private lateinit var viewModel: ProductsViewModel

    //mocks
    private val fetchProductWithReviewsUseCase: FetchProductsWithReviewsUseCase = mockk()
    private val orderReviewUseCase: OrderReviewUseCase = mockk()
    private val searchProductByNameUseCase: SearchProductByNameUseCase = mockk()
    private val productsMapper: UiMapper<ProductsWithReviewModel, ProductsWithReviews> = mockk()


    override fun setUp() {
        super.setUp()
        viewModel = ProductsViewModel(
            fetchProductWithReviewsUseCase = fetchProductWithReviewsUseCase,
            orderReviewUseCase = orderReviewUseCase,
            searchProductByNameUseCase = searchProductByNameUseCase,
            productsMapper = productsMapper
        )
    }

    @Test
    fun `should fetch users when whenStarted is called`() {
        every { productsMapper.toUiModel(any()) } returns productsWithReviews
        coEvery { fetchProductWithReviewsUseCase() } returns Result.success(
            productsWithReviewModel
        )
        val expectedState = ProductsState(
            products = productsWithReviews.productWithReviews,
            isLoading = false,
            isInError = false,
            isOnLocalCache = false
        )
        testFlow(
            flow = viewModel.state,
            expectation = {
                val actualState = it.lastOrNull()
                assertIs<ProductsState>(actualState)
                assertEquals(expectedState, actualState)
                verify(exactly = 1) { productsMapper.toUiModel(any()) }
                coVerify(exactly = 1) { fetchProductWithReviewsUseCase() }
            }
        )
    }

    @Test
    fun `should start with loading state`() {
        every { productsMapper.toUiModel(any()) } returns productsWithReviews
        coEvery { fetchProductWithReviewsUseCase() } returns Result.success(
            productsWithReviewModel
        )
        val expectedState = ProductsState(
            products = emptyList(),
            isLoading = false,
            isInError = false,
            isOnLocalCache = false
        )
        viewModel = ProductsViewModel(
            fetchProductWithReviewsUseCase = fetchProductWithReviewsUseCase,
            orderReviewUseCase = orderReviewUseCase,
            searchProductByNameUseCase = searchProductByNameUseCase,
            productsMapper = productsMapper
        )
        testFlow(
            flow = viewModel.state,
            expectation = {
                assert(it.first().products.isEmpty())
                assertEquals(expectedState, it.first())
                verify(exactly = 1) { productsMapper.toUiModel(any()) }
                coVerify(exactly = 1) { fetchProductWithReviewsUseCase() }
            }
        )
    }

    @Test
    fun `should getError when fetchProducts fails`() {
        val exception = IllegalStateException()
        every { productsMapper.toUiModel(any()) } returns productsWithReviews
        coEvery { fetchProductWithReviewsUseCase() } returns Result.failure<ProductsWithReviewModel>(
            exception
        )
        val expectedState = ProductsState(
            products = emptyList(),
            isLoading = false,
            isInError = true,
            isOnLocalCache = false
        )
        testFlow(
            flow = viewModel.state,
            expectation = {
                val actualState = it.lastOrNull()
                assertIs<ProductsState>(actualState)
                assertEquals(expectedState, actualState)
                verify(exactly = 0) { productsMapper.toUiModel(any()) }
                coVerify(exactly = 1) { fetchProductWithReviewsUseCase() }
            }
        )
    }

    @Test
    fun `should getError when mapper fails`() {
        val exception = IllegalStateException()
        every { productsMapper.toUiModel(any()) } throws exception
        coEvery { fetchProductWithReviewsUseCase() } returns Result.success(
            productsWithReviewModel
        )
        val expectedState = ProductsState(
            products = emptyList(),
            isLoading = false,
            isInError = true,
            isOnLocalCache = false
        )
        testFlow(
            flow = viewModel.state,
            expectation = {
                val actualState = it.lastOrNull()
                assertIs<ProductsState>(actualState)
                assertEquals(expectedState, actualState)
                verify(exactly = 1) { productsMapper.toUiModel(any()) }
                coVerify(exactly = 1) { fetchProductWithReviewsUseCase() }
            }
        )
    }
}

