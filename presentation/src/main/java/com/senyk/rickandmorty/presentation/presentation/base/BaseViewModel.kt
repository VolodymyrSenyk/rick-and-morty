package com.senyk.rickandmorty.presentation.presentation.base

import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.entity.MessageWithAction
import com.senyk.rickandmorty.presentation.presentation.util.livedata.NavigationEventLiveData
import com.senyk.rickandmorty.presentation.presentation.util.livedata.SingleEventLiveData
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.HandledEvent
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation.NavigationEvent
import com.senyk.rickandmorty.presentation.presentation.util.provider.ResourcesProvider
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    protected lateinit var resourcesProvider: ResourcesProvider

    protected val tag: String = this.javaClass.simpleName

    protected var _toastMessage = SingleEventLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    protected var _snackbarMessage = SingleEventLiveData<String>()
    val snackbarMessage: LiveData<String>
        get() = _snackbarMessage

    protected var _snackbarMessageWithAction = SingleEventLiveData<MessageWithAction>()
    val snackbarMessageWithAction: LiveData<MessageWithAction>
        get() = _snackbarMessageWithAction

    protected var _dialogFragment = SingleEventLiveData<DialogFragment>()
    val dialogFragment: LiveData<DialogFragment>
        get() = _dialogFragment

    protected var _showProgress = SingleEventLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    protected var _navigationEvent = NavigationEventLiveData()
    val navigationEvent: LiveData<HandledEvent<NavigationEvent>>
        get() = _navigationEvent

    protected open fun showProgress() {
        _showProgress.setValue(true)
    }

    protected open fun hideProgress() {
        _showProgress.setValue(false)
    }

    protected open fun onError(throwable: Throwable) {
        hideProgress()
        Log.e(tag, "An error occurred: ${throwable.message.toString()}", throwable)
        _toastMessage.setValue(resourcesProvider.getString(R.string.error_unknown))
    }
}
