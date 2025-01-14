package io.seph.data.repositories

import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.local.ProductReviewLocalEntity
import io.seph.data.entities.local.dao.ReviewsDao
import io.seph.data.mapper.Mapper
import io.seph.data.mapper.local.LocalMapper
import io.seph.data.services.ReviewsService
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.OrderTypeModel
import io.seph.domain.model.ProductReviewModel
import io.seph.domain.model.ProductsReviewsModel
import io.seph.domain.repositories.ProductsReviewsRepository

internal class ProductsReviewsRepositoryImpl(
    private val reviewsService: ReviewsService,
    private val reviewsDao: ReviewsDao,
    private val productReviewMapper: Mapper<ProductReviewDto, ProductReviewModel>,
    private val cacheMapper: LocalMapper<ProductReviewLocalEntity, ProductReviewDto>
) : ProductsReviewsRepository {

    private lateinit var reviewsCache: ProductsReviewsModel

    override suspend fun fetchProductsReviews(): ProductsReviewsModel {
        return when {
            this::reviewsCache.isInitialized &&
                    reviewsCache.dataSourceModel == DataSourceModel.REMOTE -> reviewsCache

            else -> try {
                val reviews = reviewsService.getProductsReviews()
                saveAllToDatabase(reviews)
                ProductsReviewsModel(
                    productsReviewsModel = reviews.map(productReviewMapper::toDomainModel),
                    dataSourceModel = DataSourceModel.REMOTE
                ).also { updateCache(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                val reviews = getAllFromDataBase()
                if (reviews.isNotEmpty()) {
                    ProductsReviewsModel(
                        productsReviewsModel = reviews.map(productReviewMapper::toDomainModel),
                        dataSourceModel = DataSourceModel.LOCAL
                    ).also { updateCache(it) }
                } else {
                    throw e
                }
            }
        }
    }

    override fun getProductsReviews(): ProductsReviewsModel {
        return if (this::reviewsCache.isInitialized) {
            reviewsCache
        } else {
            throw IllegalStateException("You must call fetchProductsReviews first")
        }
    }

    override fun updateReviewsOrders(orderType: OrderTypeModel) {
        reviewsCache = reviewsCache.copy(
            productsReviewsModel = reviewsCache.productsReviewsModel.map {
                it.copy(
                    reviews = when (orderType) {
                        OrderTypeModel.BEST_TO_WORST -> it.reviews.sortedByDescending { it.reviewRating }
                        OrderTypeModel.WORST_TO_BEST -> it.reviews.sortedBy { it.reviewRating }
                    }
                )
            }
        )
    }

    private fun updateCache(reviews: ProductsReviewsModel) {
        reviewsCache = reviews
        updateReviewsOrders(OrderTypeModel.BEST_TO_WORST)
    }

    private suspend fun saveAllToDatabase(reviews: List<ProductReviewDto>) {
        reviewsDao.deleteAll()
        reviewsDao.insertReviews(
            reviews.map(
                cacheMapper::toLocalEntity
            )
        )
    }

    private suspend fun getAllFromDataBase(): List<ProductReviewDto> {
        return reviewsDao.getReviews()
            .map(cacheMapper::toEntity)
    }
}