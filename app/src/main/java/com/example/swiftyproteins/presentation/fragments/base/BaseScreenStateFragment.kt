package com.example.swiftyproteins.presentation.fragments.base

import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.models.State
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel

abstract class BaseScreenStateFragment<
        ActionType : Action, Model : Any, ViewModel : BaseScreenStateViewModel<ActionType, Model>>
    : BaseFragment<ActionType, ViewModel>() {

    @CallSuper
    override fun setupObserve() {
        super.setupObserve()
        viewModel?.apply {
            modelUpdated.observe(viewLifecycleOwner, Observer(::handleModel))
            state.observe(viewLifecycleOwner, Observer(::handleState))
        }
    }

    abstract fun handleModel(model: Model)

    protected open fun handleState(state: State) {}

    private fun onError(error: ProteinError) {
        when (error) {
            is ProteinError.NetworkError -> {}
            is ProteinError.ProteinNotFound -> {}
        }
    }
}