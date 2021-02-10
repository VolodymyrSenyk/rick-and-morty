package com.senyk.rickandmorty.presentation.presentation.util.livedata

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleEventLiveData<T> : MutableLiveData<T>() {

    private val alreadySent = AtomicBoolean(true)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, { value: T ->
            if (alreadySent.compareAndSet(false, true)) {
                observer.onChanged(value)
            }
        })
    }

    @MainThread
    override fun setValue(value: T) {
        alreadySent.set(false)
        super.setValue(value)
    }

    @AnyThread
    override fun postValue(value: T) {
        alreadySent.set(false)
        super.postValue(value)
    }
}
