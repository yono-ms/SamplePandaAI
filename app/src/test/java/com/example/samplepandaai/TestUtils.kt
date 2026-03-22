package com.example.samplepandaai

import java.io.File

object TestUtils {
    /**
     * src/test/resources 配下のファイルを読み込む。
     */
    fun readResourceFile(fileName: String): String {
        val resource = javaClass.classLoader?.getResource(fileName)
            ?: throw IllegalArgumentException("Resource not found: $fileName")
        return File(resource.toURI()).readText()
    }
}
