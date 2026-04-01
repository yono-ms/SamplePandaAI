package com.example.samplepandaai.ui.features

import android.content.Context
import android.net.Uri
import com.example.samplepandaai.domain.usecase.IsGitHubDomainUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GitHubWebViewClientTest {

    private lateinit var context: Context
    private lateinit var isGitHubDomainUseCase: IsGitHubDomainUseCase
    private lateinit var client: GitHubWebViewClient

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        isGitHubDomainUseCase = mockk()
        client = GitHubWebViewClient(context, isGitHubDomainUseCase)

        // Uri.parse を mockk するために static mock が必要
        mockkStatic(Uri::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
    }

    @Test
    fun `handleUrl returns false when UseCase returns true`() {
        val urlStr = "https://github.com/example"
        val mockUri = mockk<Uri>()
        every { Uri.parse(urlStr) } returns mockUri
        every { mockUri.toString() } returns urlStr
        every { isGitHubDomainUseCase(urlStr) } returns true

        val result = client.handleUrl(mockUri)

        assertFalse(result)
        verify(exactly = 0) { context.startActivity(any()) }
    }

    @Test
    fun `handleUrl returns true and starts activity when UseCase returns false`() {
        val urlStr = "https://google.com"
        val mockUri = mockk<Uri>()
        every { Uri.parse(urlStr) } returns mockUri
        every { mockUri.toString() } returns urlStr
        every { isGitHubDomainUseCase(urlStr) } returns false

        val result = client.handleUrl(mockUri)

        assertTrue(result)
        verify(exactly = 1) { context.startActivity(any()) }
    }
}
