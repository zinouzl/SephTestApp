package io.seph.data.entities.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.seph.data.entities.local.ProductReviewLocalEntity

@Dao
internal interface ReviewsDao {

    @Query("SELECT * FROM ProductReviewLocalEntity")
    suspend fun getReviews(): List<ProductReviewLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ProductReviewLocalEntity>)

    @Query("DELETE FROM ProductReviewLocalEntity")
    suspend fun deleteAll()

}