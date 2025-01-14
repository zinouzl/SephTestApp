package io.seph.domain.model

data class ProductReviewModel(
    val productId: Long,
    val reviews: List<ReviewModel>
) : DomainModel