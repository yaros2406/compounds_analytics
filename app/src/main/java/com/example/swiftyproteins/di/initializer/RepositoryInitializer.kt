package com.example.swiftyproteins.di.initializer

import com.example.swiftyproteins.data.api.ClientCreator
import com.example.swiftyproteins.data.api.ProteinApiTalker
import com.example.swiftyproteins.data.mapper.ProteinMapper
import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.di.Initializer
import okhttp3.OkHttpClient
import org.koin.core.module.Module

object RepositoryInitializer: Initializer {

    private const val BASE_URL = "https://files.rcsb.org/"

    override fun initialize(appModule: Module) {
        appModule.apply {
            single {
                val client = ClientCreator().createClient(BASE_URL, OkHttpClient.Builder())
                val apiTalker = ProteinApiTalker(client)
                ProteinsRepository(apiTalker, ProteinMapper())
            }
        }
    }
}