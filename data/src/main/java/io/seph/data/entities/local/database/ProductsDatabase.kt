package io.seph.data.entities.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.seph.data.entities.local.ProductLocalEntity
import io.seph.data.entities.local.ProductReviewLocalEntity
import io.seph.data.entities.local.converters.BrandConverters
import io.seph.data.entities.local.converters.ImagesUrlsConverters
import io.seph.data.entities.local.converters.ReviewsConverters
import io.seph.data.entities.local.dao.ProductsDao
import io.seph.data.entities.local.dao.ReviewsDao

@Database(
    entities = [ProductLocalEntity::class, ProductReviewLocalEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BrandConverters::class, ReviewsConverters::class, ImagesUrlsConverters::class)
internal abstract class ProductsDatabase : RoomDatabase() {

    abstract val productsDao: ProductsDao

    abstract val reviewsDao: ReviewsDao
}