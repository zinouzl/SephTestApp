package io.seph.presentation.mapper

import io.mockk.every
import io.mockk.mockk
import io.seph.domain.model.BrandModel
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.model.ReviewModel
import io.seph.presentation.models.Brand
import io.seph.presentation.models.DataSource
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.ProductsWithReviews
import io.seph.presentation.models.Review
import org.junit.Before
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
private const val REVIEW_RATING_2 = 1f
private val reviews = listOf(
    ReviewModel(
        reviewerName = REVIEW_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING_2
    ),
)
private val productWithReview = ProductWithReviewsModel(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = PRODUCT_DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImage = PRODUCT_IMAGE,
    brand = mockBrand,
    isProductSet = IS_PRODUCT_SET,
    isSpecialBrand = isSpecialBrand,
    reviews = reviews
)
private val productsWithReview = ProductsWithReviewModel(
    productWithReviews = listOf<ProductWithReviewsModel>(
        productWithReview
    ),
    dataSourceModel = DataSourceModel.REMOTE
)

//UI
private val productWithReviewUi = ProductWithReviews(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = PRODUCT_DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImage = PRODUCT_IMAGE,
    brand = Brand(
        BRAND_ID,
        BRAND_NAME
    ),
    reviews = listOf(
        Review(
            reviewerName = REVIEW_NAME,
            reviewText = REVIEW_TEXT,
            reviewRating = REVIEW_RATING_2
        ),
    )
)
val productsWithReviewUi = ProductsWithReviews(
    productWithReviews = listOf<ProductWithReviews>(
        productWithReviewUi
    ),
    dataSourceModel = DataSource.REMOTE
)


class ProductsWithReviewsMapperTest {

    // class to test
    private lateinit var productsWithReviewsMapper: ProductsWithReviewsMapper

    private val productWithReviewsMapper: ProductWithReviewsMapper = mockk()
    private val dataSourceMapper: DataSourceMapper = mockk()

    @Before
    fun setUp() {
        productsWithReviewsMapper = ProductsWithReviewsMapper(
            productWithReviewsMapper = productWithReviewsMapper,
            dataSourceMapper = dataSourceMapper
        )
    }

    @Test
    fun `toUiModel should map ProductsWithReviewModel to ProductsWithReviews`() {
        every { productWithReviewsMapper.toUiModel(any()) } returns productWithReviewUi
        every { dataSourceMapper.toUiModel(any()) } returns DataSource.REMOTE
        val expected = productsWithReviewUi
        val actual = productsWithReviewsMapper.toUiModel(
            productsWithReview
        )
        assertEquals(expected, actual)
    }
}