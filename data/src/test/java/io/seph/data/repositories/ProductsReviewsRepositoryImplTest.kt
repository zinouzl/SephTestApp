package io.seph.data.repositories

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.seph.data.base.BaseTest
import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.ReviewDto
import io.seph.data.entities.local.ProductReviewLocalEntity
import io.seph.data.entities.local.dao.ReviewsDao
import io.seph.data.mapper.Mapper
import io.seph.data.mapper.local.LocalMapper
import io.seph.data.services.ReviewsService
import io.seph.domain.model.ProductReviewModel
import io.seph.domain.model.ReviewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

private const val PRODUCT_ID = 1978L
private const val REVIEWER_NAME = "reviewer name"
private const val REVIEW_TEXT = "review text"
private const val REVIEW_RATING = 3f
private val reviewsDao = listOf(
    ReviewDto(
        reviewerName = REVIEWER_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING
    )
)
private val productReviewDto = listOf(
    ProductReviewDto(
        productId = PRODUCT_ID, reviews = reviewsDao
    )
)

private val reviewModel = listOf(
    ReviewModel(
        reviewerName = REVIEWER_NAME,
        reviewText = REVIEW_TEXT,
        reviewRating = REVIEW_RATING
    )
)
private val productReviewModel = listOf(
    ProductReviewModel(
        productId = PRODUCT_ID,
        reviews = reviewModel
    )
)

private val reviewLocalEntity =
    ProductReviewLocalEntity(
        productId = PRODUCT_ID,
        reviews = reviewsDao
    )


class ProductsReviewsRepositoryImplTest : BaseTest() {

    // class under test
    private lateinit var productsReviewsRepository: ProductsReviewsRepositoryImpl

    private val reviewsService: ReviewsService = mockk()
    private val reviewsDao: ReviewsDao = mockk()
    private val productReviewMapper: Mapper<ProductReviewDto, ProductReviewModel> = mockk()
    private val cacheMapper: LocalMapper<ProductReviewLocalEntity, ProductReviewDto> = mockk()

    override fun setUp() {
        super.setUp()
        productsReviewsRepository = ProductsReviewsRepositoryImpl(
            reviewsService = reviewsService,
            reviewsDao = reviewsDao,
            productReviewMapper = productReviewMapper,
            cacheMapper = cacheMapper
        )
    }

    @Test
    fun `fetchProducts should return ProductsModel`() = runTest {
        coEvery { reviewsService.getProductsReviews() } returns productReviewDto
        every { productReviewMapper.toDomainModel(any()) } returns productReviewModel[0]
        coJustRun { reviewsDao.getReviews() }
        coJustRun { reviewsDao.insertReviews(any()) }
        coJustRun { reviewsDao.deleteAll() }
        every { cacheMapper.toLocalEntity(any()) } returns reviewLocalEntity
        justRun { cacheMapper.toEntity(any()) }
        val result = productsReviewsRepository.fetchProductsReviews()
        assertEquals(result.productsReviewsModel, productReviewModel)
    }

    @Test
    fun `should get error when calling getProductsReviews first`() = runTest {
        coEvery { reviewsService.getProductsReviews() } returns productReviewDto
        every { productReviewMapper.toDomainModel(any()) } returns productReviewModel[0]
        coJustRun { reviewsDao.getReviews() }
        coJustRun { reviewsDao.insertReviews(any()) }
        coJustRun { reviewsDao.deleteAll() }
        every { cacheMapper.toLocalEntity(any()) } returns reviewLocalEntity
        justRun { cacheMapper.toEntity(any()) }
        assertFails { productsReviewsRepository.getProductsReviews() }
    }

    @Test
    fun `should not catch error after fetching`() = runTest {
        coEvery { reviewsService.getProductsReviews() } returns productReviewDto
        every { productReviewMapper.toDomainModel(any()) } returns productReviewModel[0]
        coJustRun { reviewsDao.getReviews() }
        coJustRun { reviewsDao.insertReviews(any()) }
        coJustRun { reviewsDao.deleteAll() }
        every { cacheMapper.toLocalEntity(any()) } returns reviewLocalEntity
        justRun { cacheMapper.toEntity(any()) }
        productsReviewsRepository.fetchProductsReviews()
        val result = productsReviewsRepository.getProductsReviews()
        assertEquals(result.productsReviewsModel, productReviewModel)
    }

}
