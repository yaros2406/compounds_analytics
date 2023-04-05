package com.example.swiftyproteins.domain.interactor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.RawRes
import androidx.core.content.FileProvider
import com.example.swiftyproteins.R
import java.io.*

class FileInteractor(private val context: Context) {

    @RawRes
    private val proteinsFileRes: Int = R.raw.ligands
    @RawRes
    private val atomsInfoFileRes: Int = R.raw.periodic_table

    fun getProteinsListString(): List<String> {
        val inputStream: InputStream = context.resources.openRawResource(proteinsFileRes)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val fileBuilder = mutableListOf<String>()
        var newLine: String?
        while (bufferedReader.readLine().also { newLine = it } != null) {
            newLine?.let { line ->
                fileBuilder.add(line)
            }
        }
        return fileBuilder
    }

    fun readAtomsInfo(): String {
        val inputStream: InputStream = context.resources.openRawResource(atomsInfoFileRes)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonStringBuilder = StringBuilder()
        var newLine: String?
        while (bufferedReader.readLine().also { newLine = it } != null) {
            jsonStringBuilder.append(newLine)
            jsonStringBuilder.append("\n")
        }
        return jsonStringBuilder.toString()
    }

    fun saveToCacheAndGetUri(bitmap: Bitmap, fileName: String): Uri {
        val file: File = saveToCache(bitmap, fileName)
        return FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )
    }

    private fun saveToCache(bitmap: Bitmap, fileName: String): File {
        var fos: FileOutputStream? = null
        val outputStream = ByteArrayOutputStream()
        try {
            val file = getPrivateCacheFileObject(fileName)
            file.createNewFile()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val bitmapData = outputStream.toByteArray()
            fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            return file
        } finally {
            fos?.close()
            outputStream.close()
        }
    }

    private fun getPrivateCacheFileObject(name: String): File =
        File(context.cacheDir, name)
}