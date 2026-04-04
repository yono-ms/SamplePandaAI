package com.example.samplepandaai.domain.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IsSafeDomainUseCaseTest {

    private lateinit var useCase: IsSafeDomainUseCase

    @Before
    fun setUp() {
        useCase = IsSafeDomainUseCase()
    }

    @Test
    fun `invoke returns true for valid allowed domains`() {
        assertTrue(useCase("https://github.com/example/repo"))
        assertTrue(useCase("https://docs.github.com/en"))
        assertTrue(useCase("https://www.apache.org/licenses/LICENSE-2.0"))
        assertTrue(useCase("https://opensource.org/licenses/MIT"))
        assertTrue(useCase("https://ktor.io/docs/welcome.html"))
        assertTrue(useCase("https://kotlinlang.org/docs/home.html"))
    }

    @Test
    fun `invoke returns false for non-https urls`() {
        // http は許可しない
        assertFalse(useCase("http://github.com/example/repo"))
        // スキームなしは許可しない
        assertFalse(useCase("github.com/test"))
    }

    @Test
    fun `invoke returns false for other domains`() {
        assertFalse(useCase("https://google.com"))
        assertFalse(useCase("https://gitlab.com/example/repo"))
    }

    @Test
    fun `invoke returns false for special schemes`() {
        assertFalse(useCase("intent://github.com/example"))
        assertFalse(useCase("file:///android_asset/test.html"))
    }

    @Test
    fun `invoke returns false for empty or invalid strings`() {
        assertFalse(useCase(""))
        assertFalse(useCase(null))
        assertFalse(useCase("not a url"))
    }

    @Test
    fun `invoke returns false for domains ending with allowed domain but not subdomains`() {
        assertFalse(useCase("https://mygithub.com"))
        assertFalse(useCase("https://fake-ktor.io"))
    }
}
