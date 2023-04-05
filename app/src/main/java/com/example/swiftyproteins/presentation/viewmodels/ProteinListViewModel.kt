package com.example.swiftyproteins.presentation.viewmodels

import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.models.ProteinListStateScreen
import com.example.swiftyproteins.presentation.navigation.FromProteinList
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel

class ProteinListViewModel(private val interactor: ProteinInteractor) :
    BaseScreenStateViewModel<FromProteinList, ProteinListStateScreen>(
        ProteinListStateScreen(emptyList())
    ) {

    private fun updateModel(
        proteins: List<String> = model.proteins,
        scrollPosition: Int = model.scrollPosition,
        shouldUpdateScreen: Boolean = true,
    ) {
        model = ProteinListStateScreen(proteins, scrollPosition)
        if (shouldUpdateScreen) {
            handleModel()
        }
    }

    init {
        getLigands()
    }

    private fun getLigands() {
        val proteinNames: List<String> = interactor.getProteins()
        updateModel(proteinNames)
    }

    fun onSearchTextChanged(text: String) {
        interactor.setFilterForProteins(text)
        getLigands()
    }

    fun onProteinClick(proteinName: String) {
        handleAction(FromProteinList.Command.GetFirstVisibleItem)
        handleAction(FromProteinList.Navigate.Protein(proteinName))
    }

    fun onGetFirstItemPosition(position: Int) =
        updateModel(scrollPosition = position, shouldUpdateScreen = false)

    fun onBackPressed() {
        handleAction(FromProteinList.Exit)
    }
}