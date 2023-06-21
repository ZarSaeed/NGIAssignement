package com.nextgeni.domain.usecases


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means that any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> {

    abstract suspend fun run(params: Params): Flow<Type>

    suspend operator fun invoke(params: Params) : Flow<Type>  = run(params).flowOn(Dispatchers.IO)

    class None
}