package io.seph.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow


/**
 * The base class for ViewModels in the application.
 *
 * This class provides a common structure for all ViewModels, including a mechanism
 * for managing and exposing UI state through a `StateFlow`.
 *
 * It defines an inner interface [ViewModelState] to represent the data model of the UI and
 * uses a `StateFlow` named `state` to emit changes in the UI state.
 */
abstract class BaseViewModel : ViewModel() {

    interface ViewModelState

    abstract val state: StateFlow<ViewModelState>
}