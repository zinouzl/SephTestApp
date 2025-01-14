package io.seph.domain.repositories

import io.seph.domain.model.OrderTypeModel
import io.seph.domain.model.ProductsReviewsModel

interface ProductsReviewsRepository {

    suspend fun fetchProductsReviews(): ProductsReviewsModel
    fun getProductsReviews(): ProductsReviewsModel
    fun updateReviewsOrders(orderType: OrderTypeModel)
}