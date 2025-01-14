package io.seph.presentation.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Collects values from a [Flow] while respecting a [LifecycleOwner]'s lifecycle.
 *
 * The `collect` action will only run when the [LifecycleOwner] is at least in the given `minActiveState` (defaulting to STARTED).
 * It will automatically pause and resume collection based on the lifecycle state.
 *
 * @param lifecycleOwner  The [LifecycleOwner] controlling the collection.
 * @param minActiveState  The minimum lifecycle state to collect.
 * @param collect The action to perform with each emitted value.
 */
suspend fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collect: suspend (T) -> Unit
) {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(collect)
}
