package com.senyk.rickandmorty.presentation.presentation.util.livedata

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.HandledEvent

open class HandledEventLiveData<T> : MutableLiveData<HandledEvent<T>>() {

    @MainThread
    fun setHandledValue(value: T) {
        super.setValue(HandledEvent(value))
    }

    @AnyThread
    fun postHandledValue(value: T) {
        super.postValue(HandledEvent(value))
    }
}
