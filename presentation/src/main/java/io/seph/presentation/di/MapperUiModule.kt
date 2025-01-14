package io.seph.presentation.di

import io.seph.domain.model.BrandModel
import io.seph.domain.model.DataSourceModel
import io.seph.domain.model.ProductWithReviewsModel
import io.seph.domain.model.ProductsWithReviewModel
import io.seph.domain.model.ReviewModel
import io.seph.presentation.mapper.BrandMapper
import io.seph.presentation.mapper.DataSourceMapper
import io.seph.presentation.mapper.ProductWithReviewsMapper
import io.seph.presentation.mapper.ProductsWithReviewsMapper
import io.seph.presentation.mapper.ReviewMapper
import io.seph.presentation.mapper.UiMapper
import io.seph.presentation.models.Brand
import io.seph.presentation.models.DataSource
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.ProductsWithReviews
import io.seph.presentation.models.Review
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BRAND_UI_MAPPER_QUALIFIER = "BRAND_UI_MAPPER"
const val DATA_SOURCE_UI_MAPPER_QUALIFIER = "DATA_SOURCE_UI_MAPPER"
const val PRODUCT_WITH_REVIEWS_UI_MAPPER_QUALIFIER = "PRODUCT_WITH_REVIEWS_UI_MAPPER"
const val PRODUCTS_WITH_REVIEWS_UI_MAPPER_QUALIFIER = "PRODUCTS_WITH_REVIEW_UI_MAPPER"
const val REVIEW_UI_MAPPER_QUALIFIER = "REVIEW_UI_MAPPER"

/**
 * Koin module responsible for providing UI mappers.
 *
 * This module defines how to create instances of `UiMapper` for different model-to-UI object conversions.
 * It uses named qualifiers to distinguish between different mappers.
 *
 * These mappers are used to transform data models from the data layer into UI-friendly models.
 */
val mapperUiModule = module {
    single<UiMapper<BrandModel, Brand>>(named(BRAND_UI_MAPPER_QUALIFIER)) { BrandMapper() }
    single<UiMapper<ReviewModel, Review>>(named(REVIEW_UI_MAPPER_QUALIFIER)) { ReviewMapper() }
    single<UiMapper<DataSourceModel, DataSource>>(named(DATA_SOURCE_UI_MAPPER_QUALIFIER)) { DataSourceMapper() }
    single<UiMapper<ProductWithReviewsModel, ProductWithReviews>>(
        named(
            PRODUCT_WITH_REVIEWS_UI_MAPPER_QUALIFIER
        )
    ) {
        ProductWithReviewsMapper(
            get(
                named(
                    REVIEW_UI_MAPPER_QUALIFIER
                )
            ), get(
                named(
                    BRAND_UI_MAPPER_QUALIFIER
                )
            )
        )
    }

    single<UiMapper<ProductsWithReviewModel, ProductsWithReviews>>(
        named(
            PRODUCTS_WITH_REVIEWS_UI_MAPPER_QUALIFIER
        )
    ) {
        ProductsWithReviewsMapper(
            get(
                named(
                    PRODUCT_WITH_REVIEWS_UI_MAPPER_QUALIFIER
                )
            ), get(
                named(
                    DATA_SOURCE_UI_MAPPER_QUALIFIER
                )
            )
        )
    }
}
