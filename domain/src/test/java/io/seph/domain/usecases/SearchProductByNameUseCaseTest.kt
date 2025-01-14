package io.seph.domain.usecases

import io.mockk.every
import io.mockk.mockk
import io.seph.domain.model.BrandModel
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.model.ReviewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

private const val BRAND_ID = "brand_id"
private const val BRAND_NAME = "Nestor Joseph"
private val mockBrand = BrandModel(
    id = BRAND_ID,
    name = BRAND_NAME
)

private const val PRODUCT_ID = 7381L
private const val PRODUCT_NAME = "Alexandra Henry"
private const val WRONG_PRODUCT_NAME = "Alexandra Henry1"
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

private val review = listOf(
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
private val productsWithReviewModel = ProductsWithReviewModel(
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
            reviews = review
        )
    ),
    dataSourceModel = DataSourceModel.REMOTE
)

class SearchProductByNameUseCaseTest : BaseTest() {

    // class under test
    private lateinit var searchProductByNameUseCase: SearchProductByNameUseCase

    private val getProductsWithReviewsUseCase: GetProductsWithReviewsUseCase = mockk()

    override fun setUp() {
        super.setUp()
        searchProductByNameUseCase = SearchProductByNameUseCase(
            getProductsWithReviewsUseCase = getProductsWithReviewsUseCase
        )
    }

    @Test
    fun `should get the same list if product name is empty`() {
        every { getProductsWithReviewsUseCase.execute() } returns Result.success(
            productsWithReviewModel
        )
        val result = searchProductByNameUseCase.execute(SearchProductByNameUseCase.Params(""))
        assertTrue(result.isSuccess)
        assertEquals(productsWithReviewModel, result.getOrNull())
    }

    @Test
    fun `should get the list if product name was found`() {
        every { getProductsWithReviewsUseCase.execute() } returns Result.success(
            productsWithReviewModel
        )
        val result =
            searchProductByNameUseCase.execute(SearchProductByNameUseCase.Params(PRODUCT_NAME))
        assertTrue(result.isSuccess)
        assertEquals(productsWithReviewModel, result.getOrNull())
    }

    @Test
    fun `should get the list if product name was not found`() {
        every { getProductsWithReviewsUseCase.execute() } returns Result.success(
            productsWithReviewModel
        )
        val result =
            searchProductByNameUseCase.execute(SearchProductByNameUseCase.Params(WRONG_PRODUCT_NAME))
        assertTrue(result.isSuccess)
        assertEquals(
            ProductsWithReviewModel(emptyList(), DataSourceModel.REMOTE),
            result.getOrNull()
        )
    }
}