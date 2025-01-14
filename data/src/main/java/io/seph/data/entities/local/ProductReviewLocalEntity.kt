package io.seph.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.seph.data.entities.ReviewDto

/**
 * Represents a product reviews stored locally in the database.
 *
 * This entity stores the reviews associated with a specific product. The reviews are stored as a single
 * string, which contain JSON string representing the reviews.
 *
 * @property productId The unique identifier of the product this review is associated with. This is also the primary key
 *                   for the entity.
 * @property reviews A JSON string containing the reviews for the product.
 *
 * @see Entity
 */
@Entity
internal data class ProductReviewLocalEntity(
    @PrimaryKey val productId: Long,
    val reviews: List<ReviewDto>
) : LocalEntity