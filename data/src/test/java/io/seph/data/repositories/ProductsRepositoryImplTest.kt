package io.seph.data.repositories

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.seph.data.base.BaseTest
import io.seph.data.entities.BrandDto
import io.seph.data.entities.ImagesUrlsDto
import io.seph.data.entities.ProductDto
import io.seph.data.entities.local.ProductLocalEntity
import io.seph.data.entities.local.dao.ProductsDao
import io.seph.data.mapper.Mapper
import io.seph.data.mapper.local.LocalMapper
import io.seph.data.services.ProductsService
import io.seph.domain.model.BrandModel
import io.seph.domain.model.ProductModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

private const val PRODUCT_ID = 6370L
private const val PRODUCT_NAME = "product name"
private const val DESCRIPTION = "description"
private const val PRODUCT_PRICE = 10.11f
private const val IS_PRODUCT_SET = false
private const val IS_SPECIAL_BRAND = false
private const val BRAND_ID = "id"
private const val BRAND_NAME = "name"
private const val IMAGE_URL = "image url"
private val brandDto = BrandDto(
    id = BRAND_ID,
    name = BRAND_NAME
)
private val imagesUrlsDto = ImagesUrlsDto(
    small = IMAGE_URL,
    large = ""
)

private val productsDto = listOf(
    ProductDto(
        productId = PRODUCT_ID,
        productName = PRODUCT_NAME,
        description = DESCRIPTION,
        productPrice = PRODUCT_PRICE,
        productImages = imagesUrlsDto,
        brand = brandDto,
        isProductSet = IS_PRODUCT_SET,
        isSpecialBrand = IS_SPECIAL_BRAND
    )
)

private val productsLocalEntity = ProductLocalEntity(
    productId = PRODUCT_ID,
    productName = PRODUCT_NAME,
    description = DESCRIPTION,
    productPrice = PRODUCT_PRICE,
    productImage = imagesUrlsDto,
    brand = brandDto,
    isProductSet = IS_PRODUCT_SET,
    isSpecialBrand = IS_SPECIAL_BRAND
)


private val productsModel = listOf(
    ProductModel(
        productId = PRODUCT_ID,
        productName = PRODUCT_NAME,
        description = DESCRIPTION,
        productPrice = PRODUCT_PRICE,
        productImage = IMAGE_URL,
        brand = BrandModel(
            id = BRAND_ID,
            name = BRAND_NAME
        ),
        isProductSet = false,
        isSpecialBrand = false
    )
)

class ProductsRepositoryImplTest : BaseTest() {

    // class under test
    private lateinit var productsRepository: ProductsRepositoryImpl

    private val productsService: ProductsService = mockk()
    private val productsDao: ProductsDao = mockk()
    private val productMapper: Mapper<ProductDto, ProductModel> = mockk()
    private val cacheMapper: LocalMapper<ProductLocalEntity, ProductDto> = mockk()

    override fun setUp() {
        productsRepository = ProductsRepositoryImpl(
            productsService = productsService,
            productsDao = productsDao,
            productMapper = productMapper,
            cacheMapper = cacheMapper
        )
    }

    @Test
    fun `fetchProducts should return ProductsModel`() = runTest {
        coEvery { productsService.getProducts() } returns productsDto
        every { productMapper.toDomainModel(any()) } returns productsModel[0]
        coJustRun { productsDao.deleteAllProducts() }
        coJustRun { productsDao.insertProducts(any()) }
        coJustRun { productsDao.deleteAllProducts() }
        every { cacheMapper.toLocalEntity(any()) } returns productsLocalEntity
        justRun { cacheMapper.toEntity(any()) }
        val result = productsRepository.fetchProducts()
        assertEquals(result.products, productsModel)
    }

    @Test
    fun `should get error when calling getProductsReviews first`() = runTest {
        coEvery { productsService.getProducts() } returns productsDto
        every { productMapper.toDomainModel(any()) } returns productsModel[0]
        coJustRun { productsDao.deleteAllProducts() }
        coJustRun { productsDao.insertProducts(any()) }
        coJustRun { productsDao.deleteAllProducts() }
        every { cacheMapper.toLocalEntity(any()) } returns productsLocalEntity
        justRun { cacheMapper.toEntity(any()) }
        assertFails { productsRepository.getProducts() }
    }

    @Test
    fun `should not catch error after fetching`() = runTest {
        coEvery { productsService.getProducts() } returns productsDto
        every { productMapper.toDomainModel(any()) } returns productsModel[0]
        coJustRun { productsDao.deleteAllProducts() }
        coJustRun { productsDao.insertProducts(any()) }
        coJustRun { productsDao.deleteAllProducts() }
        every { cacheMapper.toLocalEntity(any()) } returns productsLocalEntity
        justRun { cacheMapper.toEntity(any()) }
        productsRepository.fetchProducts()
        val result = productsRepository.getProducts()
        assertEquals(result.products, productsModel)
    }

}