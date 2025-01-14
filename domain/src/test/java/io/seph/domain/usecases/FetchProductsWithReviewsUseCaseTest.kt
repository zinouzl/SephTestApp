package io.seph.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.seph.domain.model.BrandModel
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductModel
import io.seph.domain.model.ProductReviewModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsModel
import io.seph.domain.model.ProductsReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.model.ReviewModel
import io.seph.domain.repositories.ProductsRepository
import io.seph.domain.repositories.ProductsReviewsRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

private const val BRAND_ID = "brand_id"
private const val BRAND_NAME = "Nestor Joseph"
private val mockBrand = BrandModel(
    id = BRAND_ID,
    name = BRAND_NAME
)

private const val PRODUCT_ID = 7381L
private const val PRODUCT_NAME = "Alexandra Henry"
private const val PRODUCT_DESCRIPTION = "description"
private const val PRODUCT_PRICE = 392f
private const val PRODUCT_IMAGE = "image"
private const val IS_PRODUCT_SET = false
private const val isSpecialBrand = false
private val mockProducts = ProductsModel(
    products = listOf<ProductModel>(
        ProductModel(
            productId = PRODUCT_ID,
            productName = PRODUCT_NAME,
            description = PRODUCT_DESCRIPTION,
            productPrice = PRODUCT_PRICE,
            productImage = PRODUCT_IMAGE,
            brand = mockBrand,
            isProductSet = IS_PRODUCT_SET,
            isSpecialBrand = isSpecialBrand
        )
    ),
    dataSourceModel = DataSourceModel.REMOTE
)

private const val REVIEWER_NAME = "reviewer_name"
private const val REVIEW_TEXT = "review_text"
private const val REVIEW_RATING = 5f
private val mockProductReview = listOf(
    ProductReviewModel(
        productId = PRODUCT_ID,
        reviews = listOf(
            ReviewModel(
                reviewerName = REVIEWER_NAME,
                reviewText = REVIEW_TEXT,
                reviewRating = REVIEW_RATING
            )
        )
    )
)
private val mockReviews = ProductsReviewsModel(
    productsReviewsModel = mockProductReview,
    dataSourceModel = DataSourceModel.REMOTE
)

private val mockReviewModel = listOf(
    ReviewModel(
        reviewerName = REVIEWER_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING

    )
)

class FetchProductsWithReviewsUseCaseTest : BaseTest() {

    // class under test
    private lateinit var fetchProductsWithReviewsUseCase: FetchProductsWithReviewsUseCase

    private val mockRepository: ProductsRepository = mockk()
    private val mockReviewsRepository: ProductsReviewsRepository = mockk()

    override fun setUp() {
        super.setUp()
        fetchProductsWithReviewsUseCase = FetchProductsWithReviewsUseCase(
            dispatcher = testDispatcher,
            productsRepository = mockRepository,
            reviewsRepository = mockReviewsRepository
        )
    }

    @Test
    fun `should return ProductsWithReviewModel when fetching products and reviews successfully`() =
        runTest {
            coEvery { mockRepository.fetchProducts() } returns mockProducts
            coEvery { mockReviewsRepository.fetchProductsReviews() } returns mockReviews
            val expectedResult = ProductsWithReviewModel(
                productWithReviews = listOf(
                    ProductWithReviewsModel(
                        productId = PRODUCT_ID,
                        productName = PRODUCT_NAME,
                        description = PRODUCT_DESCRIPTION,
                        productPrice = PRODUCT_PRICE,
                        productImage = PRODUCT_IMAGE,
                        brand = mockBrand,
                        isProductSet = IS_PRODUCT_SET,
                        isSpecialBrand = isSpecialBrand,
                        reviews = mockReviewModel
                    )

                ),
                dataSourceModel = DataSourceModel.REMOTE
            )
            val result = fetchProductsWithReviewsUseCase()

            coVerify(exactly = 1) { mockRepository.fetchProducts() }
            coVerify(exactly = 1) { mockReviewsRepository.fetchProductsReviews() }
            assertTrue(result.isSuccess)
            assertEquals(expectedResult, result.getOrNull())
        }

    @Test
    fun `should return error when fetching products fails`() = runTest {
        val exception = IllegalStateException()
        coEvery { mockRepository.fetchProducts() } throws exception
        val result = fetchProductsWithReviewsUseCase()
        coVerify(exactly = 1) { mockRepository.fetchProducts() }
        coVerify(exactly = 0) { mockReviewsRepository.fetchProductsReviews() }
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `should return error when fetching reviews fails`() = runTest {
        val exception = IllegalStateException()
        coEvery { mockRepository.fetchProducts() } returns mockk()
        coEvery { mockReviewsRepository.fetchProductsReviews() } throws exception

        val result = fetchProductsWithReviewsUseCase()
        coVerify(exactly = 1) { mockRepository.fetchProducts() }
        coVerify(exactly = 1) { mockReviewsRepository.fetchProductsReviews() }
        assertEquals(exception, result.exceptionOrNull())
    }
}