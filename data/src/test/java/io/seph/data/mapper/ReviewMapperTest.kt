package io.seph.data.mapper

import io.seph.data.entities.ReviewDto
import io.seph.domain.model.ReviewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val reviewerName = "reviewer_name"
private const val reviewText = "review_text"
private const val reviewRating = 3f
private val reviewModel = ReviewModel(
    reviewerName = reviewerName,
    reviewText = reviewText,
    reviewRating = reviewRating
)
private val reviewDto = ReviewDto(
    reviewerName = reviewerName,
    reviewText = reviewText,
    reviewRating = reviewRating
)

class ReviewMapperTest {

    //class under test
    private lateinit var reviewMapper: ReviewMapper

    @Before
    fun setup() {
        reviewMapper = ReviewMapper()
    }

    @Test
    fun `toDomainModel should map ReviewDto to ReviewModel`() {
        val expectedReviewModel = reviewModel
        val actualReviewModel = reviewMapper.toDomainModel(reviewDto)
        assertEquals(expectedReviewModel, actualReviewModel)
    }

    @Test
    fun `toRaw should map ReviewModel to ReviewDto`() {
        val expectedReviewDto = reviewDto
        val actualReviewDto = reviewMapper.toRaw(reviewModel)
        assertEquals(expectedReviewDto, actualReviewDto)
    }
}