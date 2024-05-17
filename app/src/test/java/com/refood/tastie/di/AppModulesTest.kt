package com.refood.tastie.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.refood.tastie.data.datasource.cart.CartDataSource
import com.refood.tastie.data.datasource.category.CategoryDataSource
import com.refood.tastie.data.datasource.menu.MenuDataSource
import com.refood.tastie.data.repository.CartRepository
import com.refood.tastie.data.repository.CategoryRepository
import com.refood.tastie.data.repository.MenuRepository
import com.refood.tastie.data.repository.UserRepository
import com.refood.tastie.data.source.local.database.AppDatabase
import com.refood.tastie.data.source.local.pref.UserPreference
import com.refood.tastie.data.source.network.firebase.auth.FirebaseAuthDataSource
import com.refood.tastie.data.source.network.services.TastieApiService
import com.refood.tastie.presentation.cart.CartViewModel
import com.refood.tastie.presentation.checkout.CheckoutViewModel
import com.refood.tastie.presentation.detailmenu.DetailMenuViewModel
import com.refood.tastie.presentation.home.HomeViewModel
import com.refood.tastie.presentation.login.LoginViewModel
import com.refood.tastie.presentation.main.MainViewModel
import com.refood.tastie.presentation.profile.ProfileViewModel
import com.refood.tastie.presentation.register.RegisterViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@ExperimentalCoroutinesApi
class AppModulesTest {
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockFirebaseAuth: FirebaseAuth
    private lateinit var mockAppDatabase: AppDatabase
    private lateinit var mockCartDataSource: CartDataSource
    private lateinit var mockCategoryDataSource: CategoryDataSource
    private lateinit var mockMenuDataSource: MenuDataSource
    private lateinit var mockFirebaseAuthDataSource: FirebaseAuthDataSource
    private lateinit var mockTastieApiService: TastieApiService

    @Before
    fun setUp() {
        mockSharedPreferences = mockk()
        mockFirebaseAuth = mockk()
        mockAppDatabase = mockk()
        mockCartDataSource = mockk()
        mockCategoryDataSource = mockk()
        mockMenuDataSource = mockk()
        mockFirebaseAuthDataSource = mockk()
        mockTastieApiService = mockk()

        every { mockAppDatabase.cartDao() } returns mockk()
        every { mockSharedPreferences.edit() } returns mockk()
    }

    @Test
    fun `test AppModules initialization`() =
        runBlockingTest {
            startKoin {
                modules(
                    listOf(
                        module {
                            single { mockFirebaseAuth }
                            single { mockSharedPreferences }
                            single { mockAppDatabase }
                            single { mockCartDataSource }
                            single { mockCategoryDataSource }
                            single { mockMenuDataSource }
                            single { mockFirebaseAuthDataSource }
                            single { mockTastieApiService }
                            single<UserPreference> { mockk() }
                            single<CartRepository> { mockk() }
                            single<CategoryRepository> { mockk() }
                            single<MenuRepository> { mockk() }
                            single<UserRepository> { mockk() }
                            viewModel { CartViewModel(get(), get()) }
                            viewModel { CheckoutViewModel(get(), get(), get()) }
                            viewModel { DetailMenuViewModel(get(), get()) }
                            viewModel { HomeViewModel(get(), get(), get()) }
                            viewModel { LoginViewModel(get()) }
                            viewModel { MainViewModel(get()) }
                            viewModel { ProfileViewModel(get()) }
                            viewModel { RegisterViewModel(get()) }
                        },
                    ),
                )
            }

            val appModules = AppModules.modules

            assertNotNull(appModules)
            assert(appModules.isNotEmpty())
        }
}
