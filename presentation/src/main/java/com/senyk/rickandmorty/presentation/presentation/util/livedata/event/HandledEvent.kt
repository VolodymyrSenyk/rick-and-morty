package com.senyk.rickandmorty.presentation.presentation.util.livedata.event

import java.util.concurrent.atomic.AtomicBoolean

class HandledEvent<T>(private val value: T) {

    private val alreadyHandled: AtomicBoolean = AtomicBoolean(false)

    fun handleEvent(): T? {
        return if (alreadyHandled.compareAndSet(false, true)) {
            value
        } else null
    }

    val eventData: T
        get() = value
}
