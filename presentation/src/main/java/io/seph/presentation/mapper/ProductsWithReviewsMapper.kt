package io.seph.presentation.mapper

import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.presentation.models.DataSource
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.ProductsWithReviews

internal class ProductsWithReviewsMapper(
    private val productWithReviewsMapper: UiMapper<ProductWithReviewsModel, ProductWithReviews>,
    private val dataSourceMapper: UiMapper<DataSourceModel, DataSource>,
) : UiMapper<ProductsWithReviewModel, ProductsWithReviews> {
    override fun toUiModel(model: ProductsWithReviewModel): ProductsWithReviews {
        return model.run {
            ProductsWithReviews(
                productWithReviews = productWithReviews.map(productWithReviewsMapper::toUiModel),
                dataSourceModel = dataSourceMapper.toUiModel(dataSourceModel)
            )
        }
    }
}