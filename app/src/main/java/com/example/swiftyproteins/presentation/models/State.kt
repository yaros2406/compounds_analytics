package com.example.swiftyproteins.presentation.models

sealed class State {
    object Loading: State()
    object Success: State()
}