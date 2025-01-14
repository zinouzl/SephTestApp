package io.seph.domain.usecases

import io.seph.domain.common.SynchronousUseCase
import io.seph.domain.common.UseCaseParams
import io.seph.domain.model.OrderTypeModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.repositories.ProductsReviewsRepository

/**
 * A use case responsible for updating the order of reviews and then retrieving
 * the updated list of products with their reviews.
 *
 * This use case takes an [OrderTypeModel] as input, updates the review repository with the new order
 * information, and then fetches the latest product reviews data.
 *
 * @property reviewRepository The repository for managing product reviews.
 * @property getProductsWithReviewsUseCase A use case for retrieving products with their reviews.
 *
 * @return A [ProductsWithReviewModel] containing the updated products and their reviews.
 */
class OrderReviewUseCase(
    private val reviewRepository: ProductsReviewsRepository,
    private val getProductsWithReviewsUseCase: GetProductsWithReviewsUseCase
) : SynchronousUseCase<OrderReviewUseCase.Params, ProductsWithReviewModel>() {

    override fun create(params: Params) = runCatching {
        reviewRepository.updateReviewsOrders(params.order)
        return@runCatching getProductsWithReviewsUseCase.execute().getOrThrow()
    }

    @JvmInline
    value class Params(
        val order: OrderTypeModel
    ) : UseCaseParams
}