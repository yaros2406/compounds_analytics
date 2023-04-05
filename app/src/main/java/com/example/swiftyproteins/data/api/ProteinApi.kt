package com.example.swiftyproteins.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProteinApi {
    @GET("/ligands/view/{name}_ideal.pdb")
    fun getProtein(@Path("name") name: String): Call<ResponseBody>
}