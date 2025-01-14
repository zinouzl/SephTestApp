package io.seph.domain.common

import androidx.annotation.CheckResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * An interactor (use case in Clean Architecture) represents an execution unit of asynchronous work.
 * A [NoParamsSuspendingUseCase] returns a single response through a suspend function.
 *
 * Work will be executed on thread as specified by the [dispatcher] of the interactor.
 */
abstract class NoParamsSuspendingUseCase<out R> {
    /**
     * The coroutine context this interactor should execute on.
     */
    abstract val dispatcher: CoroutineDispatcher

    /**
     * Define the work to be performed by this interactor.
     */
    protected abstract suspend fun run(): Result<R>

    /**
     * By overriding invoke, we allow use cases to be called as "invoking"
     */
    @CheckResult
    suspend operator fun invoke(): Result<R> = withContext(dispatcher) {
        run()
    }
}