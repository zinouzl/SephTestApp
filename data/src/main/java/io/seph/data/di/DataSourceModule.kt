package io.seph.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.seph.data.services.ProductsService
import io.seph.data.services.ReviewsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 *  Koin module responsible for providing data source related dependencies.
 *  This module configures and provides instances of Json, Retrofit, and API service interfaces
 */
val dataSourceModule = module {

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<Json>().asConverterFactory(APPLICATION_JSON.toMediaType()))
            .build()
    }

    single<ProductsService> {
        get<Retrofit>().create(ProductsService::class.java)
    }

    single<ReviewsService> {
        get<Retrofit>().create(ReviewsService::class.java)
    }

}

private const val APPLICATION_JSON = "application/json"
private const val BASE_URL = "https://sephoraandroid.github.io/testProject/"