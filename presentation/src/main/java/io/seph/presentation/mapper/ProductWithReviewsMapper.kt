package io.seph.presentation.mapper

import io.seph.domain.model.BrandModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ReviewModel
import io.seph.presentation.models.Brand
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.Review

internal class ProductWithReviewsMapper(
    private val reviewMapper: UiMapper<ReviewModel, Review>,
    private val brandMapper: UiMapper<BrandModel, Brand>
) : UiMapper<ProductWithReviewsModel, ProductWithReviews> {

    override fun toUiModel(model: ProductWithReviewsModel): ProductWithReviews {
        return model.run {
            ProductWithReviews(
                productId = productId,
                productName = productName,
                description = description,
                productPrice = productPrice,
                productImage = productImage,
                brand = brandMapper.toUiModel(brand),
                reviews = reviews.map(reviewMapper::toUiModel)
            )
        }
    }
}