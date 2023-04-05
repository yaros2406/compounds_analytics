package com.example.swiftyproteins.data.repository

import com.example.swiftyproteins.data.api.ProteinApiTalker
import com.example.swiftyproteins.data.mapper.ProteinMapper
import com.example.swiftyproteins.data.model.ErrorType
import com.example.swiftyproteins.domain.models.Atom
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.NullPointerException

class ProteinsRepository(
    private val apiTalker: ProteinApiTalker,
    private val mapper: ProteinMapper
) {
    fun getAtomByName(name: String, onSuccess: (List<Atom>) -> Unit, onError: (ErrorType) -> Unit) {
        apiTalker.getProteinByName(
            name,
            onSuccess = { body ->
                readResource(
                    body,
                    onSuccess = { list ->
                        onSuccess(mapper.map(list))
                    },
                    onError = {
                        onError(ErrorType.NotFound)
                    }
                )
            },
            onError = onError
        )
    }

    private fun readResource(
        resource: ResponseBody,
        onSuccess: (List<String>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val inStream: InputStream = resource.byteStream()
        val reader = BufferedReader(InputStreamReader(inStream))
        var line: String
        val list = mutableListOf<String>()
        try {
            while (reader.readLine().also { line = it } != null) {
                if (line.contains(END)) {
                    break
                }
                list.add(line)
            }
            onSuccess(list)
        } catch (e: Exception) {
            if (e is NullPointerException) {
                onError(e)
            }
        }
        resource.close()
    }

    companion object {
        private const val END = "END"
    }
}