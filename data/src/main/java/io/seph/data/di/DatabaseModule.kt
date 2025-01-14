package io.seph.data.di

import androidx.room.Room
import io.seph.data.entities.local.converters.BrandConverters
import io.seph.data.entities.local.converters.ImagesUrlsConverters
import io.seph.data.entities.local.converters.ReviewsConverters
import io.seph.data.entities.local.dao.ProductsDao
import io.seph.data.entities.local.dao.ReviewsDao
import io.seph.data.entities.local.database.ProductsDatabase
import io.seph.data.util.JsonParser
import io.seph.data.util.KotlinSerializerJsonParser
import org.koin.dsl.module

/**
 * Koin module providing database related dependencies.
 *
 * This module configures the Room database, type converters, and data access objects (DAOs).
 * It also provides a JSON parser injection for serialization.
 */
val databaseModule = module {
    single {
        Room.databaseBuilder(get(), ProductsDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .addTypeConverter(get<BrandConverters>())
            .addTypeConverter(get<ImagesUrlsConverters>())
            .addTypeConverter(get<ReviewsConverters>())
            .build()
    }

    single<JsonParser> { KotlinSerializerJsonParser(get()) }
    single<BrandConverters> { BrandConverters(get()) }
    single<ImagesUrlsConverters> { ImagesUrlsConverters(get()) }
    single<ReviewsConverters> { ReviewsConverters(get()) }

    single<ProductsDao> { get<ProductsDatabase>().productsDao }
    single<ReviewsDao> { get<ProductsDatabase>().reviewsDao }
}