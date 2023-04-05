package com.example.swiftyproteins.di

import org.koin.core.module.Module

interface Initializer {
    fun initialize(appModule: Module)
}