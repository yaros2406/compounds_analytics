package com.example.swiftyproteins.presentation.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.swiftyproteins.presentation.App
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.viewmodels.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

abstract class BaseFragment<ActionType : Action, ViewModel : BaseViewModel<ActionType>>
    : Fragment() {

    protected val router by inject<Router>()
    open val viewModel: ViewModel? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val app: App = (requireActivity().applicationContext as App)
//        val viewModelFactory = app.viewModelFactory
//        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
//        viewModel = viewModelProvider.get(getViewModelClass())
//    }

    fun onBackPressed(): Boolean =
        false

//    abstract fun getViewModelClass(): Class<ViewMode>

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    @CallSuper
    protected open fun setupObserve() {
        viewModel?.apply {
            actionUpdated.observe(viewLifecycleOwner, Observer(::handleAction))
        }
    }

    abstract fun handleAction(action: ActionType)
}