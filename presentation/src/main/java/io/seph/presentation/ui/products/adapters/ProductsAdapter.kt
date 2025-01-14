package io.seph.presentation.ui.products.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.seph.presentation.R
import io.seph.presentation.databinding.ProductItemBinding
import io.seph.presentation.extensions.addCurrencySymbol
import io.seph.presentation.extensions.format
import io.seph.presentation.extensions.gone
import io.seph.presentation.extensions.visible
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.models.Review
import android.R as AndroidR

class ProductsAdapter :
    ListAdapter<ProductWithReviews, ProductsAdapter.ProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.onBind(getItem(position))


    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: ProductWithReviews) {
            with(binding) {
                root.setOnClickListener {
                    user.expended = !user.expended
                    notifyItemChanged(layoutPosition)
                }
                itemBrand.text = user.brand.name
                itemName.text = user.productName
                itemPrice.text = user.productPrice.toString().addCurrencySymbol()
                itemRating.text = user.reviewsAverage.format()
                ratingBar.rating = user.reviewsAverage.toFloat()

                Glide
                    .with(itemImage.context)
                    .load(user.productImage)
                    .error(AndroidR.drawable.ic_menu_gallery)
                    .into(itemImage)
                showHideReviews(user.reviews, user.expended)
            }
        }

        private fun showHideReviews(reviews: List<Review>, isExpanded: Boolean) {
            val reviewAdapter = ReviewsAdapter()
            with(binding) {
                reviewsList.layoutManager = LinearLayoutManager(binding.root.context)
                reviewsList.adapter = reviewAdapter
                reviewAdapter.submitList(reviews)
                if (isExpanded) {
                    groupReviewsList.visible()
                    buttonExpand.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    groupReviewsList.gone()
                    buttonExpand.setImageResource(R.drawable.ic_arrow_down)
                }
            }
        }
    }
}

private class ProductDiffCallBack : DiffUtil.ItemCallback<ProductWithReviews>() {
    override fun areItemsTheSame(
        oldItem: ProductWithReviews,
        newItem: ProductWithReviews
    ): Boolean = oldItem === newItem

    override fun areContentsTheSame(
        oldItem: ProductWithReviews,
        newItem: ProductWithReviews
    ): Boolean =
        oldItem == newItem
}