package io.seph.data.mapper

import io.seph.data.entities.BrandDto
import io.seph.data.entities.ImagesUrlsDto
import io.seph.data.entities.ProductDto
import io.seph.domain.model.BrandModel
import io.seph.domain.model.ProductModel

internal class ProductMapper(
    private val brandMapper: Mapper<BrandDto, BrandModel>
) : Mapper<ProductDto, ProductModel> {
    override fun toDomainModel(entity: ProductDto): ProductModel {
        return entity.run {
            ProductModel(
                productId = productId,
                productName = productName,
                description = description,
                productPrice = productPrice,
                productImage = productImages.small,
                brand = brandMapper.toDomainModel(brand),
                isProductSet = isProductSet,
                isSpecialBrand = isSpecialBrand
            )
        }
    }

    override fun toRaw(data: ProductModel): ProductDto {
        return data.run {
            ProductDto(
                productId = productId,
                productName = productName,
                description = description,
                productPrice = productPrice,
                productImages = ImagesUrlsDto(
                    small = productImage,
                    large = ""
                ),
                brand = brandMapper.toRaw(brand),
                isProductSet = isProductSet,
                isSpecialBrand = isSpecialBrand
            )
        }
    }
}