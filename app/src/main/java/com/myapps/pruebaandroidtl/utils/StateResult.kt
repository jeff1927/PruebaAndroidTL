package com.myapps.pruebaandroidtl.utils

sealed class StateResult<T>(
    val data: T? = null,
    val code: String? = null
) {
    class Success<T>(data: T) : StateResult<T>(data = data)
    class Failed<T>(code: String?) : StateResult<T>(code = code)
}
