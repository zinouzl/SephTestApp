package io.seph.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.seph.data.entities.BrandDto
import io.seph.data.entities.ImagesUrlsDto

/**
 * Represents a product entity stored locally in the database.
 *
 * This entity stores the product information.
 *
 * @property productId The unique identifier for the product. This serves as the primary key for the table.
 * @property productName The name of the product.
 * @property description The description of the product.
 * @property productPrice The price of the product.
 * @property productImage The URL or path to the product's image.
 * @property brand a JSON string representing the brand of the product.
 * @property isProductSet Indicates whether the product is a set or not.
 * @property isSpecialBrand Indicates whether the product is a special brand or not.
 *
 * @see Entity
 */
@Entity
internal data class ProductLocalEntity(
    @PrimaryKey val productId: Long,
    val productName: String,
    val description: String,
    val productPrice: Float,
    val productImage: ImagesUrlsDto,
    val brand: BrandDto,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean
) : LocalEntity