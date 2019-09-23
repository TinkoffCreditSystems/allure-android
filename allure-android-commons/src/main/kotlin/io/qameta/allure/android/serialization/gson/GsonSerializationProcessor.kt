package io.qameta.allure.android.serialization.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import io.qameta.allure.android.serialization.SerializationProcessor
import java.io.*

/**
 * @author Badya on 18.04.2017.
 */

object GsonSerializationProcessor : SerializationProcessor {
    private val EMPTY_STRING_BYTES = "".toByteArray(Charsets.UTF_8)

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun serialize(src: Any): String {
        return gson.toJson(src)
    }

    override fun serialize(file: File, src: Any) {
        try {
            FileWriter(file).use {
                gson.toJson(src, src.javaClass, gson.newJsonWriter(it))
            }
        } catch (e: IOException) {
            throw JsonIOException("Failed to serialize to file ${file.absolutePath}", e)
        }
    }

    override fun <T> deserialize(string: String, type: Class<T>): T {
        return gson.fromJson(string, type)
    }

    override fun <T> deserialize(file: File, type: Class<T>): T {
        try {
            FileReader(file).use {
                return gson.fromJson(gson.newJsonReader(it), type)
            }
        } catch (e: FileNotFoundException) {
            throw JsonIOException("Failed to deserialize from file ${file.absolutePath}", e)
        }
    }

}
