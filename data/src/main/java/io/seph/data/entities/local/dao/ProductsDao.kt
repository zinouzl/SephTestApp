package io.seph.data.entities.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.seph.data.entities.local.ProductLocalEntity

@Dao
internal interface ProductsDao {

    @Query("SELECT * FROM ProductLocalEntity")
    suspend fun getProducts(): List<ProductLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductLocalEntity>)

    @Query("DELETE FROM ProductLocalEntity")
    suspend fun deleteAllProducts()

}