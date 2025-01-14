package io.seph.presentation.models

data class Review(
    val reviewerName: String?,
    val reviewText: String?,
    val reviewRating: Float?
) : UiModel