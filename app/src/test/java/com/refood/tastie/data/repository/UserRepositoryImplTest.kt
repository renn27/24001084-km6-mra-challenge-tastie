package com.refood.tastie.data.repository

import android.net.Uri
import app.cash.turbine.test
import com.google.firebase.auth.FirebaseUser
import com.refood.tastie.data.model.User
import com.refood.tastie.data.source.network.firebase.auth.FirebaseAuthDataSource
import com.refood.tastie.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var ds: FirebaseAuthDataSource

    private lateinit var repo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserRepositoryImpl(ds)
    }

    @Test
    fun doLogin_success() {
        coEvery { ds.doLogin(any(), any()) } returns true
        runTest {
            repo.doLogin("khdkwa", "dghwjadga").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_error() {
        coEvery { ds.doLogin(any(), any()) } throws IllegalStateException("error")
        runTest {
            repo.doLogin("khdkwa", "dghwjadga").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_success() {
        coEvery { ds.doRegister(any(), any(), any()) } returns true
        runTest {
            repo.doRegister("khdkwa", "dghwjadga", "djwakdwakj").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_error() {
        coEvery { ds.doRegister(any(), any(), any()) } throws IllegalStateException("error")
        runTest {
            repo.doRegister("khdkwa", "dghwjadga", "dwakhda").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doLogout() {
        every { ds.doLogout() } returns true
        runTest {
            val result = repo.doLogout()
            assertTrue(result)
            verify { ds.doLogout() }
        }
    }

    @Test
    fun isLoggedIn() {
        every { ds.isLoggedIn() } returns true
        runTest {
            val result = repo.isLoggedIn()
            assertTrue(result)
            verify { ds.isLoggedIn() }
        }
    }

    @Test
    fun getCurrentUser_returnNull() {
        every { ds.getCurrentUser() } returns null
        runTest {
            val result = repo.getCurrentUser()
            assertEquals(null, result)
            verify { ds.getCurrentUser() }
        }
    }

    @Test
    fun getCurrentUser_returnUser() {
        val mockFirebaseUser = mockk<FirebaseUser>()
        every { mockFirebaseUser.displayName } returns "dhakwd"
        every { mockFirebaseUser.email } returns "dhwak"
        every { mockFirebaseUser.photoUrl } returns mockk(relaxed = true)
        val mockUri = mockk<Uri>()
        every { mockUri.toString() } returns "dhawdaj"
        every { mockFirebaseUser.photoUrl } returns mockUri
        val expectedUser =
            User(
                email = "dhwak",
                fullName = "dhakwd",
                photoUrl = "dhawdaj",
            )
        every { ds.getCurrentUser() } returns mockFirebaseUser
        runTest {
            val result = repo.getCurrentUser()
            assertEquals(expectedUser, result)
            verify { ds.getCurrentUser() }
        }
    }

    @Test
    fun updateProfile_success() {
        val photoUri: Uri = mockk()
        coEvery { ds.updateProfile(any(), any()) } returns true
        runTest {
            repo.updateProfile("khdkwa", photoUri).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun updateProfile_null() {
        coEvery { ds.updateProfile(null, null) } returns true
        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun updateProfile_error() {
        val photoUri: Uri = mockk()
        coEvery { ds.updateProfile(any(), any()) } throws IllegalStateException("error")
        runTest {
            repo.updateProfile("khdkwa", photoUri).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun updatePassword_success() {
        coEvery { ds.updatePassword(any()) } returns true
        runTest {
            repo.updatePassword("khdkwa").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updatePassword_error() {
        coEvery { ds.updatePassword(any()) } throws IllegalStateException("error")
        runTest {
            repo.updatePassword("khdkwa").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updateEmail_success() {
        coEvery { ds.updateEmail(any()) } returns true
        runTest {
            repo.updateEmail("khdkwa").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.updateEmail(any()) }
            }
        }
    }

    @Test
    fun updateEmail_error() {
        coEvery { ds.updateEmail(any()) } throws IllegalStateException("error")
        runTest {
            repo.updateEmail("khdkwa").map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ds.updateEmail(any()) }
            }
        }
    }

    @Test
    fun sendChangePasswordRequestByEmail() {
        every { ds.sendChangePasswordRequestByEmail() } returns true
        runTest {
            val result = repo.sendChangePasswordRequestByEmail()
            assertTrue(result)
            verify { ds.sendChangePasswordRequestByEmail() }
        }
    }
}
