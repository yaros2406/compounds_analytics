package com.example.swiftyproteins.presentation.navigation

sealed class FromProteinList : Action {
    object Exit : FromProteinList()

    sealed class BackTo : FromProteinList() {
        object Login : BackTo()
    }

    sealed class Navigate : FromProteinList() {
        class Protein(val proteinName: String) : Navigate()
    }

    sealed class Command : FromProteinList() {
        object GetFirstVisibleItem : Command()
    }
}