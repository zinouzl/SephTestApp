package io.seph.domain.model

data class ProductsWithReviewModel(
    val productWithReviews: List<ProductWithReviewsModel>,
    val dataSourceModel: DataSourceModel
) : DomainModel {
    constructor(productsResult: ProductsModel, reviewsResult: ProductsReviewsModel) : this(
        productWithReviews = buildList {
            addAll(
                productsResult.products.map { product ->
                    ProductWithReviewsModel(
                        productId = product.productId,
                        productName = product.productName,
                        description = product.description,
                        productPrice = product.productPrice,
                        productImage = product.productImage,
                        brand = product.brand,
                        isProductSet = product.isProductSet,
                        isSpecialBrand = product.isSpecialBrand,
                        reviews = reviewsResult.productsReviewsModel.find { product.productId == it.productId }
                            ?.reviews
                            ?: emptyList<ReviewModel>()
                    )
                }
            )
        },
        dataSourceModel = when (productsResult.dataSourceModel) {
            DataSourceModel.LOCAL -> DataSourceModel.LOCAL

            else -> reviewsResult.dataSourceModel
        }
    )
}