package io.seph.domain.usecases

import io.seph.domain.common.SynchronousUseCase
import io.seph.domain.model.ProductsWithReviewModel

/**
 * A use case responsible for searching products by name or brand name.
 *
 * This use case retrieves all products with their reviews and filters them based on the provided
 * product name. The filtering is not case-insensitive and checks if the product name or brand name contains
 * the search terms.
 *
 * @property getProductsWithReviewsUseCase A use case to retrieve all products with their reviews.
 *
 * @return A [ProductsWithReviewModel] containing the filtered products and their reviews.
 */
class SearchProductByNameUseCase(
    private val getProductsWithReviewsUseCase: GetProductsWithReviewsUseCase
) : SynchronousUseCase<SearchProductByNameUseCase.Params, ProductsWithReviewModel>() {

    override fun create(params: Params): Result<ProductsWithReviewModel> = runCatching {
        val productsWithReviews = getProductsWithReviewsUseCase.execute().getOrThrow()
        return@runCatching if (params.productName.isNotBlank())
            productsWithReviews.copy(
                productWithReviews = productsWithReviews.productWithReviews.filter {
                    it.productName.contains(params.productName, true) ||
                            it.brand.name.contains(params.productName, true)
                }
            )
        else {
            productsWithReviews
        }
    }

    @JvmInline
    value class Params(
        val productName: String
    )
}