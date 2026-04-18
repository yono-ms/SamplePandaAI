package com.example.samplepandaai.ui.navigation

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class DestinationsTest {

    @Test
    fun license_isSerializable() {
        val json = Json.encodeToString(License)
        // License is an object, so it should be represented by its fully qualified name or similar depending on setup, 
        // but basically we just want to ensure it doesn't throw and decodes back.
        val decoded = Json.decodeFromString<License>(json)
        assertEquals(License, decoded)
    }

    @Test
    fun repoList_isSerializable() {
        val repoList = RepoList(username = "testuser")
        val json = Json.encodeToString(repoList)
        val decoded = Json.decodeFromString<RepoList>(json)
        assertEquals(repoList, decoded)
        assertEquals("testuser", decoded.username)
    }
}
