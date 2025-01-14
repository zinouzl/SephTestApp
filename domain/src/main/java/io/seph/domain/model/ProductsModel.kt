package io.seph.domain.model

data class ProductsModel(
    val products: List<ProductModel>,
    val dataSourceModel: DataSourceModel
) : DomainModel