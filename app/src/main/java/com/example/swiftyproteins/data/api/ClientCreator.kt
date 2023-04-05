package com.example.swiftyproteins.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class ClientCreator {
    fun createClient(
        baseUrl: String,
        okHttpClientBuilder: OkHttpClient.Builder
    ): ProteinApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .build()
            .create(ProteinApi::class.java)
}