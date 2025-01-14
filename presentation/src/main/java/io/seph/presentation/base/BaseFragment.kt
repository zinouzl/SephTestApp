package io.seph.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import io.seph.presentation.extensions.collectWithLifecycle
import kotlinx.coroutines.launch

/**
 * the base fragment class for Fragments that utilizes ViewBinding and a ViewModel.
 *
 * This class provides a common structure for setting up view binding, managing view lifecycle,
 * and observing ViewModel state changes in a Fragment. It enforces the use of a ViewBinding class
 * and a BaseViewModel instance.
 *
 * @param Binding The type of ViewBinding class associated with the Fragment.
 * @param VM The type of BaseViewModel class associated with the Fragment.
 */
abstract class BaseFragment<Binding : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: Binding

    protected abstract val viewModel: VM

    abstract fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setup()
        lifecycleScope.launch {
            viewModel.state.collectWithLifecycle(viewLifecycleOwner) { state ->
                observeState(state)
            }
        }
    }

    abstract fun Binding.setup()

    abstract suspend fun observeState(state: BaseViewModel.ViewModelState)
}