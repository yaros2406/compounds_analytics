package com.example.swiftyproteins.presentation.viewmodels.base

import androidx.lifecycle.MutableLiveData
import com.example.swiftyproteins.presentation.models.State
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.view.controller.SingleEvent

abstract class BaseScreenStateViewModel<ActionType : Action, Model : Any>(initModel: Model)
    : BaseViewModel<ActionType>() {

    protected var model: Model = initModel

    val modelUpdated = MutableLiveData<Model>()
    val state: MutableLiveData<State> = SingleEvent()

    protected fun handleModel() {
        modelUpdated.value = model
    }

    protected fun handleState(state: State) {
        this.state.value = state
    }
}