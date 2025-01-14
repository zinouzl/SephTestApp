package io.seph.presentation.ui.products.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.seph.presentation.R
import io.seph.presentation.databinding.ReveiwItemBinding
import io.seph.presentation.models.Review
import java.util.Locale

class ReviewsAdapter : ListAdapter<Review, ReviewsAdapter.ReviewViewHolder>(ReviewDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(
            ReveiwItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ReviewViewHolder(private val binding: ReveiwItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(review: Review) {
            with(binding) {
                itemName.text = review.reviewerName
                    ?.replaceFirstChar {
                        it.titlecase(Locale.getDefault())
                    } ?: getString(root.context, R.string.unknown)
                itemRating.text = review.reviewRating?.toString() ?: "--"
                ratingBar.rating = review.reviewRating ?: 0f
                itemComment.text = review.reviewText
            }
        }
    }

}

private class ReviewDiffCallBack : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(
        oldItem: Review,
        newItem: Review
    ): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ): Boolean =
        oldItem == newItem
}