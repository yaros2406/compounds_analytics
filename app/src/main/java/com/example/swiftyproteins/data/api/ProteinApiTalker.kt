package com.example.swiftyproteins.data.api

import com.example.swiftyproteins.data.model.ErrorType
import com.example.swiftyproteins.presentation.logD
import com.example.swiftyproteins.presentation.logE
import okhttp3.ResponseBody

class ProteinApiTalker(
    private val client: ProteinApi
): BaseApiTalker() {

    fun getProteinByName(proteinName: String, onSuccess: (ResponseBody) -> Unit, onError: (ErrorType) -> Unit) {
        getResult(
            client.getProtein(proteinName),
            onSuccess =  { body ->
                onSuccess(body)
            },
            onError = { errorCode, errorMessage ->
                logE(errorMessage)
                when (errorCode) {
                    NETWORK_ERROR_CODE -> onError(ErrorType.Network)
                    NOT_FOUND_ERROR_CODE -> onError(ErrorType.NotFound)
                    else -> {
                        onError(ErrorType.Unknown)
                        logE(errorMessage)
                    }
                }
            }
        )
    }

    companion object {
        private const val NETWORK_ERROR_CODE = 0
        private const val NOT_FOUND_ERROR_CODE = 404
    }
}