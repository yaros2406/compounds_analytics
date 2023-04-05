package com.example.swiftyproteins.presentation.viewmodels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.view.controller.SingleEvent

abstract class BaseViewModel<ActionType: Action>: ViewModel() {

    private val action: MutableLiveData<ActionType> = SingleEvent()
    val actionUpdated: LiveData<ActionType> = action

    protected fun handleAction(action: ActionType) {
        this.action.value = action
    }
}