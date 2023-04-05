package com.example.swiftyproteins.di.initializer

import com.example.swiftyproteins.di.Initializer
import com.example.swiftyproteins.presentation.mapper.ProteinMapper
import com.example.swiftyproteins.presentation.viewmodels.LoginViewModel
import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel
import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

object ViewModelInitializer : Initializer {
    override fun initialize(appModule: Module) {
        appModule.apply {
            viewModel {
                LoginViewModel()
            }

            viewModel {
                ProteinListViewModel(get())
            }

            viewModel { (proteinName: String?) ->
                ProteinViewModel(proteinName, get(), ProteinMapper())
            }
        }
    }
}