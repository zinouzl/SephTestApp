package io.seph.presentation.di

import io.seph.presentation.ui.products.ProductsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for providing viewModels.
 *
 * using Koin's dependency injection capabilities. It specifies the required dependencies,
 * which will be automatically injected when the viewModel is requested.
 *
 */
val viewModelModule = module {
    viewModel<ProductsViewModel> {
        ProductsViewModel(
            get(),
            get(),
            get(),
            get(named(PRODUCTS_WITH_REVIEWS_UI_MAPPER_QUALIFIER))
        )
    }
}