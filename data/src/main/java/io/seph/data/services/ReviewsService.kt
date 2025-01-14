package io.seph.data.services

import io.seph.data.entities.ProductReviewDto
import retrofit2.http.GET

/**
 *  the Retrofit interface defines the contract for fetching product reviews data from a remote source.
 */
internal interface ReviewsService {

    @GET("reviews.json")
    suspend fun getProductsReviews(): List<ProductReviewDto>
}