package io.seph.data.di

import io.seph.data.entities.BrandDto
import io.seph.data.entities.ProductDto
import io.seph.data.entities.ProductReviewDto
import io.seph.data.entities.ReviewDto
import io.seph.data.entities.local.ProductLocalEntity
import io.seph.data.entities.local.ProductReviewLocalEntity
import io.seph.data.mapper.BrandMapper
import io.seph.data.mapper.Mapper
import io.seph.data.mapper.ProductMapper
import io.seph.data.mapper.ProductReviewMapper
import io.seph.data.mapper.ReviewMapper
import io.seph.data.mapper.local.LocalMapper
import io.seph.data.mapper.local.ProductReviewLocalMapper
import io.seph.data.mapper.local.ProductsLocalMapper
import io.seph.domain.model.BrandModel
import io.seph.domain.model.ProductModel
import io.seph.domain.model.ProductReviewModel
import io.seph.domain.model.ReviewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DATA_PRODUCT_MAPPER = "DATA_PRODUCT_MAPPER"
const val DATA_BRAND_MAPPER = "DATA_BRAND_MAPPER"
const val DATA_REVIEW_MAPPER = "DATA_REVIEW_MAPPER"
const val DATA_PRODUCT_REVIEW_MAPPER = "DATA_PRODUCT_REVIEW_MAPPER"

const val LOCAL_DATA_PRODUCT_MAPPER = "LOCAL_DATA_PRODUCT_MAPPER"
const val LOCAL_DATA_REVIEW_MAPPER = "LOCAL_DATA_REVIEW_MAPPER"

/**
 * Koin module providing mappers for data layer.
 *
 * This module defines how to create instances of mapper classes used for transforming data
 * between domain, data layers, and local entities (database).
 *
 * Each mapper is registered with a specific name using [named] qualifier for easy access within Koin.
 *
 * @see Mapper
 * @see LocalMapper
 */
val dataMapperModule = module {
    single<Mapper<BrandDto, BrandModel>>(named(DATA_BRAND_MAPPER)) { BrandMapper() }
    single<Mapper<ProductDto, ProductModel>>(named(DATA_PRODUCT_MAPPER)) {
        ProductMapper(
            get(
                named(
                    DATA_BRAND_MAPPER
                )
            )
        )
    }
    single<Mapper<ReviewDto, ReviewModel>>(named(DATA_REVIEW_MAPPER)) { ReviewMapper() }
    single<Mapper<ProductReviewDto, ProductReviewModel>>(named(DATA_PRODUCT_REVIEW_MAPPER)) {
        ProductReviewMapper(
            get(named(DATA_REVIEW_MAPPER))
        )
    }
    single<LocalMapper<ProductReviewLocalEntity, ProductReviewDto>>(named(LOCAL_DATA_REVIEW_MAPPER)) { ProductReviewLocalMapper() }
    single<LocalMapper<ProductLocalEntity, ProductDto>>(named(LOCAL_DATA_PRODUCT_MAPPER)) { ProductsLocalMapper() }
}