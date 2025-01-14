package io.seph.domain.di

import io.seph.domain.usecases.FetchProductsWithReviewsUseCase
import io.seph.domain.usecases.GetProductsWithReviewsUseCase
import io.seph.domain.usecases.OrderReviewUseCase
import io.seph.domain.usecases.SearchProductByNameUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

/**
 *  Koin module providing dependencies related to the domain layer.
 *
 *  This module defines how to create instances of use cases.
 *
 */
val domainModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    factory { FetchProductsWithReviewsUseCase(get(), get(), get()) }
    factory { GetProductsWithReviewsUseCase(get(), get()) }
    factory { OrderReviewUseCase(get(), get()) }
    factory { SearchProductByNameUseCase(get()) }
}