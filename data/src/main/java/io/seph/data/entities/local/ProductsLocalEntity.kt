package io.seph.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a product entity stored locally in the database.
 *
 * This entity stores the product information.
 *
 * @property productId The unique identifier for the product. This serves as the primary key for the table.
 * @property productName The name of the product.
 * @property productPrice The price of the product.
 * @property productImage The URL or path to the product's image.
 * @property brand a JSON string representing the brand of the product.
 *
 * @see Entity
 */
@Entity
internal data class ProductsLocalEntity(
    @PrimaryKey val productId: Long,
    val productName: String,
    val productPrice: Float,
    val productImage: String,
    val brand: String,
) : LocalEntity