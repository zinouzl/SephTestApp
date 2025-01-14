package io.seph.data.mapper.local

import io.seph.data.entities.ProductDto
import io.seph.data.entities.local.ProductLocalEntity

internal class ProductsLocalMapper : LocalMapper<ProductLocalEntity, ProductDto> {
    override fun toEntity(local: ProductLocalEntity): ProductDto {
        return local.run {
            ProductDto(
                productId = productId,
                productName = productName,
                description = description,
                productPrice = productPrice,
                productImages = productImage,
                brand = brand,
                isProductSet = isProductSet,
                isSpecialBrand = isSpecialBrand
            )
        }
    }

    override fun toLocalEntity(entity: ProductDto): ProductLocalEntity {
        return entity.run {
            ProductLocalEntity(
                productId = productId,
                productName = productName,
                description = description,
                productPrice = productPrice,
                productImage = productImages,
                brand = brand,
                isProductSet = isProductSet,
                isSpecialBrand = isSpecialBrand
            )
        }
    }
}