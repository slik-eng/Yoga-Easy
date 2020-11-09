package com.slikeng.yogaeasytest.util

import io.reactivex.Observable

sealed class Async<out T> {
    class Running<out T> : Async<T>()
    data class Success<out T>(val data: T) : Async<T>()
    data class Failure<out T>(val error: Throwable) : Async<T>()
}

fun <T, R> Observable<Async<T>>.mapAsyncSuccess(body: (T) -> R): Observable<Async<R>> {
    return this.map {
        when (it) {
            is Async.Success -> Async.Success(body(it.data))
            is Async.Failure -> Async.Failure(it.error)
            is Async.Running -> Async.Running<R>()
        }
    }
}

fun <U, V> Observable<Async<U>>.mapAsyncSuccessSafely(transformer: (U) -> V): Observable<Async<V>> {
    return mapAsyncSuccess(transformer).onErrorReturn { Async.Failure(it) }
}

fun <T> Observable<Async<T>>.doOnAsyncSuccess(data: (T) -> Unit): Observable<Async<T>> {
    return doOnNext { result ->
        when (result) {
            is Async.Success -> data(result.data)
        }
    }
}

fun <T> Observable<Async<T>>.doOnAsyncFailure(handler: (Throwable) -> Unit): Observable<Async<T>> {
    return doOnNext { result ->
        when (result) {
            is Async.Failure -> handler(result.error)
        }
    }
}


fun <T> Observable<T>.mapToAsync(): Observable<Async<T>> {
    return this
        .map {
            Async.Success(it) as Async<T>
        }
        .onErrorReturn {
            Async.Failure(it)
        }
}