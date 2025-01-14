package io.seph.domain.repositories

import io.seph.domain.model.ProductsModel

interface ProductsRepository {

    suspend fun fetchProducts(): ProductsModel
    fun getProducts(): ProductsModel
}