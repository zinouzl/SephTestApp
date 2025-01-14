package io.seph.domain.model

data class ProductsReviewsModel(
    val productsReviewsModel: List<ProductReviewModel>,
    val dataSourceModel: DataSourceModel
) : DomainModel