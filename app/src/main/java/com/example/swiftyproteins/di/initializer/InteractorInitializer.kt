package com.example.swiftyproteins.di.initializer

import com.example.swiftyproteins.di.Initializer
import com.example.swiftyproteins.domain.interactor.FileInteractor
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.domain.mapper.ProteinMapper
import org.koin.core.module.Module

object InteractorInitializer: Initializer {
    override fun initialize(appModule: Module) {
        appModule.apply {
            single {
                FileInteractor(get())
            }

            single {
                ProteinInteractor(get(), get(), ProteinMapper())
            }
        }
    }
}