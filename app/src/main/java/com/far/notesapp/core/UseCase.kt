package com.far.notesapp.core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

abstract class UseCase<T, in Params> {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val ioScope = Dispatchers.IO
    private val mainScope = Dispatchers.Main

    abstract fun run(params: Params? = null): Flow<T>

    operator fun invoke(
        params: Params? = null,
        onError: ((Throwable) -> Unit) = {},
        onSuccess: (T) -> Unit = {}
    ) {
        applicationScope.launch(ioScope) {
            try {
                run(params).collect {
                    withContext(mainScope) {
                        onSuccess(it)
                    }
                }
            } catch (e: Exception) {
                withContext(mainScope) {
                    onError(e)
                }
            }
        }
    }
}