package io.seph.data.util

import kotlin.reflect.KType

/**
 * An interface for parsing and serializing JSON data.
 *
 * This interface defines methods for converting JSON strings to objects and vice versa.
 * It allows you to specify the desired type for conversion using Java's `Type` interface,
 * which enables parsing to complex types like parameterized collections or generic classes.
 */
interface JsonParser {
    fun <T> fromJson(json: String, type: KType): T

    fun <T> toJson(obj: T, type: KType): String
}