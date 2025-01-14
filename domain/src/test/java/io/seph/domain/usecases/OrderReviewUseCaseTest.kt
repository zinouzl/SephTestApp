package io.seph.domain.usecases

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.seph.domain.model.BrandModel
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.OrderTypeModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.model.ReviewModel
import io.seph.domain.repositories.ProductsReviewsRepository
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
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
private const val REVIEW_NAME = "name"
private const val REVIEW_TEXT = "text"
private const val REVIEW_RATING_1 = 5f
private const val REVIEW_RATING_2 = 1f
// mock data for best to worst

private val reviewFromBestToWorst = listOf(
    ReviewModel(
        reviewerName = REVIEW_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING_1
    ),
    ReviewModel(
        reviewerName = REVIEW_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING_2
    )
)
private val productsWithReviewModelBestToWorst = ProductsWithReviewModel(
    productWithReviews = listOf<ProductWithReviewsModel>(
        ProductWithReviewsModel(
            productId = PRODUCT_ID,
            productName = PRODUCT_NAME,
            description = PRODUCT_DESCRIPTION,
            productPrice = PRODUCT_PRICE,
            productImage = PRODUCT_IMAGE,
            brand = mockBrand,
            isProductSet = IS_PRODUCT_SET,
            isSpecialBrand = isSpecialBrand,
            reviews = reviewFromBestToWorst
        )
    ),
    dataSourceModel = DataSourceModel.REMOTE
)

// mock data for worst to best
private val reviewFromWorstToBest = listOf(
    ReviewModel(
        reviewerName = REVIEW_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING_2
    ),
    ReviewModel(
        reviewerName = REVIEW_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING_1
    )
)
private val productsWithReviewModelWorstToBest = ProductsWithReviewModel(
    productWithReviews = listOf<ProductWithReviewsModel>(
        ProductWithReviewsModel(
            productId = PRODUCT_ID,
            productName = PRODUCT_NAME,
            description = PRODUCT_DESCRIPTION,
            productPrice = PRODUCT_PRICE,
            productImage = PRODUCT_IMAGE,
            brand = mockBrand,
            isProductSet = IS_PRODUCT_SET,
            isSpecialBrand = isSpecialBrand,
            reviews = reviewFromWorstToBest
        )
    ),
    dataSourceModel = DataSourceModel.REMOTE
)

class OrderReviewUseCaseTest : BaseTest() {

    // class under test
    private lateinit var orderReviewUseCase: OrderReviewUseCase

    private val getProductsWithReviewsUseCase: GetProductsWithReviewsUseCase = mockk()
    private val mockReviewsRepository: ProductsReviewsRepository = mockk()

    override fun setUp() {
        super.setUp()
        orderReviewUseCase = OrderReviewUseCase(
            getProductsWithReviewsUseCase = getProductsWithReviewsUseCase,
            reviewRepository = mockReviewsRepository
        )
    }

    @Test
    fun `should return correct order of reviews for best to worst choice`() {
        every { getProductsWithReviewsUseCase.execute() } returns Result.success(
            productsWithReviewModelBestToWorst
        )
        justRun { mockReviewsRepository.updateReviewsOrders(any()) }

        val result =
            orderReviewUseCase.execute(OrderReviewUseCase.Params(OrderTypeModel.BEST_TO_WORST))
        assertTrue(result.isSuccess)
        assertEquals(productsWithReviewModelBestToWorst, result.getOrNull())
    }

    @Test
    fun `should return correct order of reviews for worst to best choice`() {
        every { getProductsWithReviewsUseCase.execute() } returns Result.success(
            productsWithReviewModelWorstToBest
        )
        justRun { mockReviewsRepository.updateReviewsOrders(any()) }

        val result =
            orderReviewUseCase.execute(OrderReviewUseCase.Params(OrderTypeModel.WORST_TO_BEST))
        assertTrue(result.isSuccess)
        assertEquals(productsWithReviewModelWorstToBest, result.getOrNull())
    }
}