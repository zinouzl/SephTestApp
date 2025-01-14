package io.seph.data.repositories

import io.seph.data.entities.ProductDto
import io.seph.data.entities.local.ProductLocalEntity
import io.seph.data.entities.local.dao.ProductsDao
import io.seph.data.mapper.Mapper
import io.seph.data.mapper.local.LocalMapper
import io.seph.data.services.ProductsService
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductModel
import io.seph.domain.model.ProductsModel
import io.seph.domain.repositories.ProductsRepository

internal class ProductsRepositoryImpl(
    private val productsService: ProductsService,
    private val productsDao: ProductsDao,
    private val productMapper: Mapper<ProductDto, ProductModel>,
    private val cacheMapper: LocalMapper<ProductLocalEntity, ProductDto>
) : ProductsRepository {

    private lateinit var productsCache: ProductsModel

    override suspend fun fetchProducts(): ProductsModel {
        return when {
            this::productsCache.isInitialized &&
                    productsCache.dataSourceModel == DataSourceModel.REMOTE -> productsCache

            else -> try {
                val products = productsService.getProducts()
                saveAllToDatabase(products)
                ProductsModel(
                    products = products.map(productMapper::toDomainModel),
                    dataSourceModel = DataSourceModel.REMOTE
                ).also { updateCache(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                val products = getAllFromDataBase()
                if (products.isNotEmpty()) {
                    ProductsModel(
                        products = products.map(productMapper::toDomainModel),
                        dataSourceModel = DataSourceModel.LOCAL
                    ).also { updateCache(it) }
                } else {
                    throw e
                }
            }
        }
    }

    override fun getProducts(): ProductsModel {
        return if (this::productsCache.isInitialized) {
            productsCache
        } else {
            throw IllegalStateException("You must call fetchProducts first")
        }
    }

    private fun updateCache(reviews: ProductsModel) {
        productsCache = reviews
    }

    private suspend fun saveAllToDatabase(products: List<ProductDto>) {
        productsDao.deleteAllProducts()
        productsDao.insertProducts(
            products.map(
                cacheMapper::toLocalEntity
            )
        )
    }

    private suspend fun getAllFromDataBase(): List<ProductDto> {
        return productsDao.getProducts()
            .map(cacheMapper::toEntity)
    }

}