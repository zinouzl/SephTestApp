package io.seph.domain.usecases

import io.seph.domain.common.SynchronousNoParamUseCase
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.repositories.ProductsRepository
import io.seph.domain.repositories.ProductsReviewsRepository

/**
 * A use case class responsible for retrieving from memory cache the list of products along with their associated reviews.
 *
 * @property productsRepository The repository responsible for fetching product information.
 * @property reviewsRepository The repository responsible for fetching product reviews.
 *
 * @return A [ProductsWithReviewModel] containing the list of products and their associated reviews.
 */
class GetProductsWithReviewsUseCase(
    private val productsRepository: ProductsRepository,
    private val reviewsRepository: ProductsReviewsRepository
) : SynchronousNoParamUseCase<ProductsWithReviewModel>() {

    override fun create(): Result<ProductsWithReviewModel> = runCatching {
        return@runCatching ProductsWithReviewModel(
            productsRepository.getProducts(),
            reviewsRepository.getProductsReviews()
        )
    }
}