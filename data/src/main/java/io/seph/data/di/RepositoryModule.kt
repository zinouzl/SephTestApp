package io.seph.data.di

import io.seph.data.repositories.ProductsRepositoryImpl
import io.seph.data.repositories.ProductsReviewsRepositoryImpl
import io.seph.domain.repositories.ProductsRepository
import io.seph.domain.repositories.ProductsReviewsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 *  Koin module that provides dependencies related to data repositories.
 */
val repositoryModule = module {
    single<ProductsRepository> {
        ProductsRepositoryImpl(
            get(),
            get(),
            get(named(DATA_PRODUCT_MAPPER)),
            get(named(LOCAL_DATA_PRODUCT_MAPPER))
        )
    }
    single<ProductsReviewsRepository> {
        ProductsReviewsRepositoryImpl(
            get(),
            get(),
            get(named(DATA_PRODUCT_REVIEW_MAPPER)),
            get(named(LOCAL_DATA_REVIEW_MAPPER))
        )
    }
}