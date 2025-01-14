package io.seph.data.mapper

import io.mockk.every
import io.mockk.mockk
import io.seph.data.entities.BrandDto
import io.seph.data.entities.ImagesUrlsDto
import io.seph.data.entities.ProductDto
import io.seph.domain.model.BrandModel
import io.seph.domain.model.ProductModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

private const val PRODUCT_ID = 34L
private const val PRODUCT_NAME = "product_name"
private const val PRODUCT_DESCRIPTION = "description"
private const val PRODUCT_PRICE = 392f
private const val PRODUCT_IMAGE = "image"
private const val BRAND_ID = "brand_id"
private const val BRAND_NAME = "brand_name"
private const val IS_PRODUCT_SET = false
private const val IS_SPECIAL_BRAND = false
private val brandModel = BrandModel(
    id = BRAND_ID,
    name = BRAND_NAME
)
private val productModel = ProductModel(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = PRODUCT_DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImage = PRODUCT_IMAGE,
    brand = brandModel,
    isProductSet = IS_PRODUCT_SET,
    isSpecialBrand = IS_SPECIAL_BRAND
)

private val PRODUCT_IMAGES = ImagesUrlsDto(
    small = PRODUCT_IMAGE,
    large = ""
)
private val brandDto = BrandDto(
    id = BRAND_ID,
    name = BRAND_NAME
)
private val productDto = ProductDto(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = PRODUCT_DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImages = PRODUCT_IMAGES,
    brand = brandDto,
    isProductSet = IS_PRODUCT_SET,
    isSpecialBrand = IS_SPECIAL_BRAND
)

class ProductMapperTest {

    //class to test
    private lateinit var productMapper: ProductMapper

    private val mockBrandMapper: BrandMapper = mockk()

    @Before
    fun setup() {
        productMapper = ProductMapper(mockBrandMapper)
    }


    @Test
    fun `toDomainModel should map ProductDto to ProductModel`() {
        every { mockBrandMapper.toDomainModel(brandDto) } returns brandModel
        val expectedProductModel = productModel
        val actualProductModel = productMapper.toDomainModel(productDto)
        assertEquals(expectedProductModel, actualProductModel)
    }

    @Test
    fun `toRaw should map ProductModel to ProductDto`() {
        every { mockBrandMapper.toRaw(brandModel) } returns brandDto
        val expectedProductModel = productDto
        val actualProductModel = productMapper.toRaw(productModel)
        assertEquals(expectedProductModel, actualProductModel)
    }

}