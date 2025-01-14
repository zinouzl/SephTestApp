package io.seph.domain.common

/**
 * An interactor (use case in Clean Architecture) representing a synchronous work.
 *
 * This class provides a template for use cases that execute synchronously,
 *
 * @param I The type of input parameters required by the use case.
 * @param O The type of output returned by the use case upon successful execution.
 */
abstract class SynchronousUseCase<I, O> {
    protected abstract fun create(params: I): Result<O>

    /**
     * Executes the operation with the given parameters.
     *
     * @param params The input parameters required by the use case.
     * @return A [Result] containing either the successful output of type [O] or an error.
     */
    fun execute(params: I): Result<O> = create(params)
}