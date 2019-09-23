package io.qameta.allure.android.serialization

import java.io.File

/**
 * @author Badya on 18.04.2017.
 */
interface SerializationProcessor : Serializer, Deserializer

interface Serializer {
    fun serialize(src: Any): String
    fun serialize(file: File, src: Any)
}

interface Deserializer {
    fun <T> deserialize(string: String, type: Class<T>): T
    fun <T> deserialize(file: File, type: Class<T>): T
}

