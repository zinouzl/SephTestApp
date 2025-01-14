package io.seph.data.mapper.local

import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.ReviewDto
import io.seph.data.entities.local.ProductReviewLocalEntity
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

private const val PRODUCT_ID = 34L
private const val REVIEWER_NAME = "reviewer_name"
private const val REVIEW_TEXT = "review_text"
private const val REVIEW_RATING = 3f
private val reviewsDto = listOf<ReviewDto>(
    ReviewDto(
        reviewerName = REVIEWER_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING
    )
)
private val productReviewDto = ProductReviewDto(
    productId = PRODUCT_ID,
    reviews = reviewsDto
)

private val productReviewLocalEntity = ProductReviewLocalEntity(
    productId = PRODUCT_ID,
    reviews = reviewsDto
)

class ProductReviewLocalMapperTest {

    private lateinit var productReviewLocalMapper: ProductReviewLocalMapper

    @Before
    fun setup() {
        productReviewLocalMapper = ProductReviewLocalMapper()
    }

    @Test
    fun `toEntity should map ProductReviewLocalEntity to ProductReviewDto`() {
        val expectedProductReviewDto = productReviewDto
        val actualProductReviewDto = productReviewLocalMapper.toEntity(productReviewLocalEntity)
        assertEquals(expectedProductReviewDto, actualProductReviewDto)
    }

    @Test
    fun `toLocalEntity should map ProductReviewDto to ProductReviewLocalEntity`() {
        val expectedProductReviewLocalEntity = productReviewLocalEntity
        val actualProductReviewLocalEntity =
            productReviewLocalMapper.toLocalEntity(productReviewDto)
        assertEquals(expectedProductReviewLocalEntity, actualProductReviewLocalEntity)
    }

}