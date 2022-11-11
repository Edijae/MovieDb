package com.samuel.data.utilities

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T : Any?> Flow<Result<T>>.applyEffects() =
    onStart { emit(Result.Progress(true)) }.onCompletion {
        emit(Result.Progress(false))
    }