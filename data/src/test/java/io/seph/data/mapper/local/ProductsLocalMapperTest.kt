package io.seph.data.mapper.local

import io.seph.data.entities.BrandDto
import io.seph.data.entities.ImagesUrlsDto
import io.seph.data.entities.ProductDto
import io.seph.data.entities.local.ProductLocalEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val PRODUCT_ID = 34L
private const val PRODUCT_NAME = "product_name"
private const val DESCRIPTION = "description"
private const val PRODUCT_PRICE = 34f
private const val PRODUCT_IMAGE = "product_image"
private const val BRAND_ID = "brand_id"
private const val BRAND_NAME = "brand"
private const val isProductSet = true
private const val isSpecialBrand = true
private val brandDto = BrandDto(
    id = BRAND_ID,
    name = BRAND_NAME
)
private val imagesUrlsDto = ImagesUrlsDto(
    small = PRODUCT_IMAGE,
    large = ""
)
private val productDto = ProductDto(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImages = imagesUrlsDto,
    brand = brandDto,
    isProductSet = isProductSet,
    isSpecialBrand = isSpecialBrand
)

private val productLocalEntity = ProductLocalEntity(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImage = imagesUrlsDto,
    brand = brandDto,
    isProductSet = isProductSet,
    isSpecialBrand = isSpecialBrand
)

class ProductsLocalMapperTest {

    // class under test
    private lateinit var productsLocalMapper: ProductsLocalMapper

    @Before
    fun setup() {
        productsLocalMapper = ProductsLocalMapper()
    }

    @Test
    fun `toEntity should map ProductLocalEntity to ProductDto`() {
        val expectedProductDto = productDto
        val actualProductDto = productsLocalMapper.toEntity(productLocalEntity)
        assertEquals(expectedProductDto, actualProductDto)
    }

    @Test
    fun `toLocalEntity should map ProductDto to ProductLocalEntity`() {
        val expectedProductLocalEntity = productLocalEntity
        val actualProductLocalEntity = productsLocalMapper.toLocalEntity(productDto)
        assertEquals(expectedProductLocalEntity, actualProductLocalEntity)
    }
}