package io.seph.presentation.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.seph.domain.model.OrderTypeModel
import io.seph.presentation.R
import io.seph.presentation.base.BaseFragment
import io.seph.presentation.base.BaseViewModel
import io.seph.presentation.databinding.FragmentProductsBinding
import io.seph.presentation.extensions.gone
import io.seph.presentation.extensions.hideKeyboard
import io.seph.presentation.extensions.visible
import io.seph.presentation.models.ProductWithReviews
import io.seph.presentation.ui.products.adapters.ProductsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>() {

    override val viewModel: ProductsViewModel by viewModel<ProductsViewModel>()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductsBinding = FragmentProductsBinding.inflate(inflater, container, false)

    /**
     * Sets up all UI components and listeners for the product screen.
     *
     * @receiver FragmentProductsBinding The UI binding for the product screen.
     */
    override fun FragmentProductsBinding.setup() {
        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ProductsAdapter()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            editSearch.text?.clear()
            viewModel.reorderReview(
                when (checkedId) {
                    radioBestToWorst.id -> OrderTypeModel.BEST_TO_WORST
                    radioWorstToBest.id -> OrderTypeModel.WORST_TO_BEST
                    else -> OrderTypeModel.BEST_TO_WORST
                }
            )
        }

        refreshButton.setOnClickListener {
            viewModel.fetchProducts()
        }

        textInputLayout.setEndIconOnClickListener {
            viewModel.searchProductsByName(editSearch.text.toString())
            textInputLayout.hideKeyboard()
        }
    }

    /**
     * Observes and reacts to changes in the viewModel state.
     *
     * This function takes a state object from the viewModel and updates the UI accordingly.
     *
     * @param state The current state of the product list, cast to ProductsState.
     *
     * @see BaseViewModel.ViewModelState
     * @see BaseFragment.observeState
     */
    override suspend fun observeState(state: BaseViewModel.ViewModelState) {
        state as? ProductsViewModel.ProductsState ?: return
        handleLoading(state.isLoading)
        handleLocalMode(state.isOnLocalCache)
        handleError(state.isInError, state.isOnLocalCache)
        handleProducts(state.products)
    }

    /**
     * Updates the RecyclerView with a new list of products.
     *
     * @param products The list of [ProductWithReviews] to display.
     */
    private fun handleProducts(products: List<ProductWithReviews>) {
        (binding.recyclerProducts.adapter as? ProductsAdapter)?.submitList(products)
    }

    /**
     * Updates the UI based on whether we're using database data.
     *
     * If `isOnLocalCache` is true, shows a snackbar, warning text, and a refresh button to let the user know the data might be old.
     * Otherwise, hides these elements.
     *
     * @param isOnLocalCache `true` if using cached data, `false` otherwise.
     */
    private fun handleLocalMode(isOnLocalCache: Boolean) {
        if (isOnLocalCache) {
            showSnackBar()
            binding.textWarning.visible()
            binding.refreshButton.visible()
        } else {
            binding.refreshButton.gone()
            binding.textWarning.gone()
        }
    }

    /**
     * Shows/hides the refresh button and a cat error animation based on whether an error occurred.
     *
     * @param inError `true` if there's an error, `false` otherwise.
     * @param isOnLocalCache `true` if using cached data, `false` otherwise.
     */
    private fun handleError(inError: Boolean, isOnLocalCache: Boolean) {
        when {
            inError -> {
                binding.refreshButton.visible()
                binding.lottieError.visible()
            }

            isOnLocalCache -> {
                binding.refreshButton.visible()
            }

            else -> {
                binding.refreshButton.gone()
                binding.lottieError.gone()
            }
        }
    }

    /**
     * Shows a short warning message as a Snackbar.
     */
    private fun showSnackBar() {
        Snackbar.make(
            binding.refreshButton,
            getString(R.string.error_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    /**
     * Shows or hides the loading UI component based on the provided parameter.
     *
     * @param loading True to show, false to hide.
     */
    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visible()
        } else {
            binding.progressBar.gone()
        }
    }
}