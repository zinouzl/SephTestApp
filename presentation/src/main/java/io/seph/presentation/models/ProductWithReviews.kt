package io.seph.presentation.models

data class ProductWithReviews(
    val productId: Long,
    val productName: String,
    val description: String,
    val productPrice: Float,
    val productImage: String,
    val brand: Brand,
    var reviews: List<Review>,
    var expended: Boolean = false
) : UiModel {

    val reviewsAverage: Double
        get() = reviews.mapNotNull { it.reviewRating }.average()
}