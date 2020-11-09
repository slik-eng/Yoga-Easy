package com.slikeng.yogaeasytest.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Composite [Disposable] that takes in a [LifecycleOwner] and disposes itself during onDestroy.
 * Comes in handy when using disposables in activities or services.
 */
class CompositeLifecycleDisposable(lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    private var disposables: CompositeDisposable? = null


    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dispose()
    }

    fun add(disposable: Disposable): Boolean {
        if (disposables == null) {
            disposables = CompositeDisposable()
        }
         disposables?.let { return it.add(disposable) }

        return false
    }

    fun dispose() {
        disposables.let { it?.dispose() }
        disposables = null
    }

    fun clear() {
        disposables.let { it?.clear() }
        disposables = null

    }
}

operator fun CompositeLifecycleDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}