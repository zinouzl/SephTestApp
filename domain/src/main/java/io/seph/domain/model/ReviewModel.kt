package io.seph.domain.model

data class ReviewModel(
    val reviewerName: String?,
    val reviewText: String?,
    val reviewRating: Float?
) : DomainModel