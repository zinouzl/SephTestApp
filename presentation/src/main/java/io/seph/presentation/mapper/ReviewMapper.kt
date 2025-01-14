package io.seph.presentation.mapper

import io.seph.domain.model.ReviewModel
import io.seph.presentation.models.Review

class ReviewMapper : UiMapper<ReviewModel, Review> {
    override fun toUiModel(model: ReviewModel): Review {
        return model.run {
            Review(
                reviewerName = reviewerName,
                reviewText = reviewText,
                reviewRating = reviewRating
            )
        }
    }
}