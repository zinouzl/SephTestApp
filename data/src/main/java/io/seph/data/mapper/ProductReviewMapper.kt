package io.seph.data.mapper

import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.ReviewDto
import io.seph.domain.model.ProductReviewModel
import io.seph.domain.model.ReviewModel

internal class ProductReviewMapper(
    private val reviewMapper: Mapper<ReviewDto, ReviewModel>
) : Mapper<ProductReviewDto, ProductReviewModel> {

    override fun toDomainModel(entity: ProductReviewDto): ProductReviewModel {
        return entity.run {
            ProductReviewModel(
                productId = productId,
                reviews = reviews.map(reviewMapper::toDomainModel)
            )
        }
    }

    override fun toRaw(data: ProductReviewModel): ProductReviewDto {
        return data.run {
            ProductReviewDto(
                productId = productId,
                reviews = reviews.map(reviewMapper::toRaw)
            )
        }
    }
}