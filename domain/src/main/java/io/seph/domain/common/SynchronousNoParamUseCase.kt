package io.seph.domain.common

/**
 * An abstract class representing a synchronous
 * use case that does not require any input parameters.
 *
 * Subclasses of this class should implement the [create]
 * method to define the actual logic of the use case.
 *
 * @param O The type of the output result of the use case.
 */
abstract class SynchronousNoParamUseCase<out O> {
    protected abstract fun create(): Result<O>

    /**
     * Executes the action that creates the result.
     *
     * @return A [Result] containing either the successful outcome [O] or a failure.
     */
    fun execute(): Result<O> = create()
}