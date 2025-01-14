package io.seph.presentation.mapper


import io.seph.domain.model.ReviewModel
import io.seph.presentation.models.Review
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

private const val REVIEW_NAME = "name"
private const val REVIEW_TEXT = "text"
private const val REVIEW_RATING_2 = 1f

class ReviewMapperTest {

    // class to test
    private lateinit var reviewMapper: ReviewMapper

    @Before
    fun setup() {
        reviewMapper = ReviewMapper()
    }

    @Test
    fun `should map ReviewModel to Review`() {
        val reviewModel = ReviewModel(
            reviewerName = REVIEW_NAME,
            reviewText = REVIEW_TEXT,
            reviewRating = REVIEW_RATING_2
        )

        val expectedReview = Review(
            reviewerName = REVIEW_NAME,
            reviewText = REVIEW_TEXT,
            reviewRating = REVIEW_RATING_2
        )

        val actualReview = reviewMapper.toUiModel(reviewModel)
        assertEquals(expectedReview, actualReview)
    }

}