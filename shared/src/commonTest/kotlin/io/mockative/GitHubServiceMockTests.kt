package io.mockative

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GitHubServiceMockTests {

    @Mock val github = mock(GitHubAPI::class)

    private val service = GitHubService(github, ApplicationDispatchers.Unconfined)

    @AfterTest
    fun validateMocks() {
        verify(github).hasNoUnmetExpectations()
    }

    @Test
    fun whenCallingCreate_thenCreateIsCalled() = runBlockingTest {
        // given
        val repository = Repository(id = "mockative/mockative", name = "Mockative")

        // when
        service.create(repository)

        // then
        verify(github).coroutine { create(repository) }
            .wasInvoked(exactly = once)
    }

    @Test
    fun givenSetupOfSuspendingCommand_whenCallingCommand_thenMockIsUsed() = runBlockingTest {
        // given
        val id = "0efb1b3b-f1b2-41f8-a1d8-368027cc86ee"
        val repository = Repository(id, "Mockative")

        given(github).coroutine { repository(id) }
            .thenReturn(repository)

        // when
        val result = service.repository(id)

        // then
        assertEquals(repository, result)
    }

    @Test
    fun givenSetupOfSuspendingCommand_whenCallingCommand_thenMockIsUsed_dispatched() = dispatchBlockingTest {
        // given
        val id = "0efb1b3b-f1b2-41f8-a1d8-368027cc86ee"
        val repository = Repository(id, "Mockative")

        given(github).coroutine { repository(id) }
            .thenReturn(repository)

        // when
        val result = service.repository(id)

        // then
        assertEquals(repository, result)
    }

    @Test
    fun givenSetupOfSuspendingCommand_whenCallingCommand_thenMockIsCalled() = runBlockingTest {
        // given
        val id = "0efb1b3b-f1b2-41f8-a1d8-368027cc86ee"
        val repository = Repository(id, "Mockative")

        given(github).coroutine { repository(id) }
            .thenReturn(repository)

        // when
        service.repository(id)

        // then
        verify(github).coroutine { repository(id) }
            .wasInvoked(exactly = once)

        verify(github).hasNoUnverifiedExpectations()
    }
}