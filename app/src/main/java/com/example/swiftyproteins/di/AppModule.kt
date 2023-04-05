package com.example.swiftyproteins.di

import com.example.swiftyproteins.di.initializer.InteractorInitializer
import com.example.swiftyproteins.di.initializer.NavigationInitializer
import com.example.swiftyproteins.di.initializer.RepositoryInitializer
import com.example.swiftyproteins.di.initializer.ViewModelInitializer
import org.koin.dsl.module

val AppModule = module {
    listOf(
        RepositoryInitializer,
        InteractorInitializer,
        NavigationInitializer,
        ViewModelInitializer
    ).forEach { initializer ->
        initializer.initialize(this)
    }
}