package io.seph.presentation.models


data class ProductsWithReviews(
    val productWithReviews: List<ProductWithReviews>,
    val dataSourceModel: DataSource
) : UiModel