package com.example.samplepandaai.util.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.URISyntaxException

/**
 * java.net.URI 用のカスタムシリアライザー。
 * 不正な形式のURLが含まれていた場合、SerializationException をスローして安全に処理を中断する。
 */
object URIKSerializer : KSerializer<URI> {
    private val logger = LoggerFactory.getLogger(URIKSerializer::class.java)

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("URI", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: URI) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): URI {
        val string = decoder.decodeString()
        return try {
            URI(string)
        } catch (e: URISyntaxException) {
            logger.error("Invalid URI format: $string", e)
            throw SerializationException("Invalid URI format: $string", e)
        } catch (e: IllegalArgumentException) {
            logger.error("Malformed URI: $string", e)
            throw SerializationException("Malformed URI: $string", e)
        }
    }
}
