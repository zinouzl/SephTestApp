package io.seph.data.mapper.local

import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.local.ProductReviewLocalEntity

internal class ProductReviewLocalMapper :
    LocalMapper<ProductReviewLocalEntity, ProductReviewDto> {
    override fun toEntity(local: ProductReviewLocalEntity): ProductReviewDto {
        return local.run {
            ProductReviewDto(
                productId = productId,
                reviews = reviews
            )
        }
    }

    override fun toLocalEntity(entity: ProductReviewDto): ProductReviewLocalEntity {
        return entity.run {
            ProductReviewLocalEntity(
                productId = productId,
                reviews = reviews
            )
        }
    }

}