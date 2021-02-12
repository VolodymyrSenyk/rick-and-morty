package com.senyk.rickandmorty.presentation.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.senyk.rickandmorty.presentation.presentation.entity.MessageWithAction
import com.senyk.rickandmorty.presentation.presentation.util.extensions.hideKeyboard
import com.senyk.rickandmorty.presentation.presentation.util.factory.ViewModelFactory
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @get:LayoutRes
    protected abstract val layoutRes: Int

    @get:MenuRes
    protected open val menuRes: Int? = null

    protected abstract val viewModel: BaseViewModel

    protected lateinit var binding: B

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBaseObservers(viewModel)
        setHasOptionsMenu(menuRes != null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuRes?.let { inflater.inflate(it, menu) }
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    override fun onPause() {
        hideSnackbar()
        super.onPause()
    }

    protected open fun setBaseObservers(viewModel: BaseViewModel) {
        viewModel.toastMessage.observe(viewLifecycleOwner, { showToast(it) })
        viewModel.snackbarMessage.observe(viewLifecycleOwner, { showSnackbar(it) })
        viewModel.snackbarMessageWithAction.observe(viewLifecycleOwner, {
            showSnackbarWithAction(it)
        })
        viewModel.dialogFragment.observe(viewLifecycleOwner, { showDialogFragment(it) })
        viewModel.navigationEvent.observe(viewLifecycleOwner, {
            handleNavigationEvent(it.handleEvent())
        })
    }

    protected open fun handleNavigationEvent(event: NavigationEvent?) {
        when (event) {
            is NavigateToActivityEvent -> {
                startActivity(Intent(requireActivity(), event.destination))
                if (event.finishCurrentActivity) requireActivity().finish()
            }

            is StartActivityEvent -> {
                startActivity(event.intent)
            }

            is StartActivityForResultEvent -> {
                startActivityForResult(event.intent, event.requestCode)
            }

            is RequestPermissionsEvent -> {
                requestPermissions(event.permissions.toTypedArray(), event.requestCode)
            }

            is StartServiceEvent -> {
                requireActivity().startService(event.intent)
            }

            is NavigateToFragmentEvent -> {
                findNavController().navigate(event.action)
            }

            is NavigateBackEvent -> {
                findNavController().popBackStack()
            }

            is NavigateBackToFragmentEvent -> {
                findNavController().popBackStack(event.destination, false)
            }
        }
    }

    protected open fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    protected open fun showSnackbar(message: String) {
        view?.let { view ->
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
                duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            }
            snackbar?.show()
        }
    }

    protected open fun showSnackbarWithAction(data: MessageWithAction) {
        view?.let { view ->
            snackbar = Snackbar.make(view, data.text, Snackbar.LENGTH_INDEFINITE)
                .setAction(data.actionName, data.action)
            snackbar?.show()
        }
    }

    protected open fun hideSnackbar() {
        snackbar?.dismiss()
    }

    protected open fun showDialogFragment(dialogFragment: DialogFragment) {
        dialogFragment.setTargetFragment(this, RC_DIALOG_FRAGMENT)
        dialogFragment.show(parentFragmentManager, dialogFragment.tag)
    }

    companion object {
        private const val RC_DIALOG_FRAGMENT = 1
    }
}
