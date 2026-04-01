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
    fun `invoke returns true for github_com`() {
        assertTrue(useCase("https://github.com/example/repo"))
    }

    @Test
    fun `invoke returns true for subdomains of github_com`() {
        assertTrue(useCase("https://docs.github.com/en"))
        assertTrue(useCase("https://gist.github.com/user/123"))
    }

    @Test
    fun `invoke returns false for other domains`() {
        assertFalse(useCase("https://google.com"))
        assertFalse(useCase("https://gitlab.com/example/repo"))
    }

    @Test
    fun `invoke returns false for empty or invalid strings`() {
        assertFalse(useCase(""))
        assertFalse(useCase(null))
        assertFalse(useCase("not a url"))
    }

    @Test
    fun `invoke returns true for github urls without scheme`() {
        // extractHost の現在の実装ではスキームがない場合も考慮している
        assertTrue(useCase("github.com/test"))
    }

    @Test
    fun `invoke returns false for domains ending with github_com but not subdomains`() {
        // mygithub.com は許可しない
        assertFalse(useCase("https://mygithub.com"))
    }
}
