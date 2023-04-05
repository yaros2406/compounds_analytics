package com.example.swiftyproteins.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseApiTalker {

    fun getResult(
        call: Call<ResponseBody>,
        onSuccess: (ResponseBody) -> Unit,
        onError: (Int, String) -> Unit
    ) {
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val pokemonResource: ResponseBody? = response.body()
                if (response.isSuccessful && pokemonResource != null) {
                    onSuccess.invoke(pokemonResource)
                } else {
                    onError(response.code(), " ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(0, NETWORK_FAIL + t.message)
            }
        })
    }

    companion object {
        private const val NETWORK_FAIL = "Network call has failed for a following reason: "
    }
}