package io.seph.data.services

import io.seph.data.entities.ProductDto
import retrofit2.http.GET

/**
 *  the Retrofit interface defines the contract for fetching product data from a remote source.
 */
internal interface ProductsService {

    @GET("items.json")
    suspend fun getProducts(): List<ProductDto>
}