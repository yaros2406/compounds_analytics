package com.example.swiftyproteins.di.initializer

import com.example.swiftyproteins.di.Initializer
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.module.Module

object NavigationInitializer : Initializer {
    override fun initialize(appModule: Module) {
        appModule.apply {
            single { Cicerone.create() }
            single { get<Cicerone<Router>>().router }
            single { get<Cicerone<Router>>().getNavigatorHolder() }
        }
    }
}