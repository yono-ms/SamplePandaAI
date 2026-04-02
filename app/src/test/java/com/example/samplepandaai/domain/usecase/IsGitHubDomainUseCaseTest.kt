package com.example.samplepandaai.domain.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IsGitHubDomainUseCaseTest {

    private lateinit var useCase: IsGitHubDomainUseCase

    @Before
    fun setUp() {
        useCase = IsGitHubDomainUseCase()
    }

    @Test
    fun `invoke returns true for valid https github_com urls`() {
        assertTrue(useCase("https://github.com/example/repo"))
        assertTrue(useCase("https://docs.github.com/en"))
    }

    @Test
    fun `invoke returns false for non-https github urls`() {
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
    fun `invoke returns false for domains ending with github_com but not subdomains`() {
        assertFalse(useCase("https://mygithub.com"))
    }
}
