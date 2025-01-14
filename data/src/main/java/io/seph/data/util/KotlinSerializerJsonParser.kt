package io.seph.data.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

/**
 * A [JsonParser] implementation that utilizes Kotlin Serialization for JSON parsing and serialization.
 *
 * This class leverages the power of Kotlin Serialization to seamlessly handle JSON conversion
 * to and from Kotlin objects. It relies on the provided [Json] instance for configuration and
 * the [kotlinx.serialization.serializer] function to dynamically obtain the necessary serializers.
 *
 * @property json The [Json] instance used for JSON parsing and serialization.
 */
@Suppress("UNCHECKED_CAST")
class KotlinSerializerJsonParser(private val json: Json) : JsonParser {

    /**
     * Deserializes a JSON string into an object of the specified type [T].
     *
     * This function uses kotlinx.serialization to perform the deserialization.
     *
     * @param jsonString The JSON string to deserialize.
     * @param type The type of the object to deserialize into.
     * @return An object of type T, deserialized from the JSON string.
     * @throws kotlinx.serialization.SerializationException If there is an issue during deserialization,
     *          such as an invalid JSON string or a type mismatch.
     * @throws IllegalArgumentException If no serializer can be found for the given type.
     *
     * @param T The type of the object to deserialize. This is inferred from the 'type' parameter.
     */
    override fun <T> fromJson(jsonString: String, type: KType): T {
        val serializer = serializer(type) as KSerializer<T>
        return json.decodeFromString(serializer, jsonString)
    }

    /**
     * Converts an object of type [T] to a JSON string.
     *
     * This function uses kotlinx.serialization to perform the serialization.
     *
     * @param obj The object to serialize to JSON.
     * @param type The [Type] of the object. This is used to obtain the correct [KSerializer].
     * @return A JSON string representation of the object.
     *
     * @throws kotlinx.serialization.SerializationException If there is an issue during deserialization,
     *          such as an invalid JSON string or a type mismatch.
     * @throws IllegalArgumentException If no serializer can be found for the given type.
     *
     * @see serializer
     * @see Json.encodeToString
     */
    override fun <T> toJson(obj: T, type: KType): String {
        val serializer = serializer(type) as KSerializer<T>
        return json.encodeToString(serializer, obj)
    }
}