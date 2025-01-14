package io.seph.data.mapper

import io.mockk.every
import io.mockk.mockk
import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.ReviewDto
import io.seph.domain.model.ProductReviewModel
import io.seph.domain.model.ReviewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val PRODUCT_ID = 3948L
private const val REVIEWER_NAME = "reviewer_name"
private const val REVIEW_TEXT = "review_text"
private const val REVIEW_RATING = 3f
private val reviewDto = listOf(
    ReviewDto(
        reviewerName = REVIEWER_NAME,
        reviewRating = REVIEW_RATING,
        reviewText = REVIEW_TEXT
    )
)
private val productReviewDto = ProductReviewDto(
    productId = PRODUCT_ID,
    reviews = reviewDto
)

private val reviewModel = listOf(
    ReviewModel(
        reviewerName = REVIEWER_NAME,
        reviewRating = REVIEW_RATING,
        reviewText = REVIEW_TEXT
    )
)
private val productReviewModel = ProductReviewModel(
    productId = PRODUCT_ID,
    reviews = reviewModel
)

class ProductReviewMapperTest {

    // class under test
    private lateinit var productReviewMapper: ProductReviewMapper

    private val reviewMapper: ReviewMapper = mockk()

    @Before
    fun setup() {
        productReviewMapper = ProductReviewMapper(reviewMapper)
    }

    @Test
    fun `toDomainModel should map ProductReviewDto to ProductReviewModel`() {
        every { reviewMapper.toDomainModel(reviewDto.first()) } returns reviewModel.first()
        val expectedProductReviewModel = productReviewModel
        val actualProductReviewModel = productReviewMapper.toDomainModel(productReviewDto)
        assertEquals(expectedProductReviewModel, actualProductReviewModel)
    }

    @Test
    fun `toRaw should map ProductReviewModel to ProductReviewDto`() {
        every { reviewMapper.toRaw(reviewModel.first()) } returns reviewDto.first()
        val expectedProductReviewModel = productReviewDto
        val actualProductReviewModel = productReviewMapper.toRaw(productReviewModel)
        assertEquals(expectedProductReviewModel, actualProductReviewModel)
    }

}