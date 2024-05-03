package com.refood.tastie.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.refood.tastie.data.datasource.cart.CartDataSource
import com.refood.tastie.data.datasource.cart.CartDatabaseDataSource
import com.refood.tastie.data.datasource.category.CategoryApiDataSource
import com.refood.tastie.data.datasource.category.CategoryDataSource
import com.refood.tastie.data.datasource.menu.MenuApiDataSource
import com.refood.tastie.data.datasource.menu.MenuDataSource
import com.refood.tastie.data.repository.CartRepository
import com.refood.tastie.data.repository.CartRepositoryImpl
import com.refood.tastie.data.repository.CategoryRepository
import com.refood.tastie.data.repository.CategoryRepositoryImpl
import com.refood.tastie.data.repository.MenuRepository
import com.refood.tastie.data.repository.MenuRepositoryImpl
import com.refood.tastie.data.repository.UserRepository
import com.refood.tastie.data.repository.UserRepositoryImpl
import com.refood.tastie.data.source.local.database.AppDatabase
import com.refood.tastie.data.source.local.database.dao.CartDao
import com.refood.tastie.data.source.local.pref.UserPreference
import com.refood.tastie.data.source.local.pref.UserPreferenceImpl
import com.refood.tastie.data.source.network.firebase.auth.FirebaseAuthDataSource
import com.refood.tastie.data.source.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.refood.tastie.data.source.network.services.TastieApiService
import com.refood.tastie.presentation.cart.CartViewModel
import com.refood.tastie.presentation.checkout.CheckoutViewModel
import com.refood.tastie.presentation.detailmenu.DetailMenuViewModel
import com.refood.tastie.presentation.home.HomeViewModel
import com.refood.tastie.presentation.login.LoginViewModel
import com.refood.tastie.presentation.main.MainViewModel
import com.refood.tastie.presentation.profile.ProfileViewModel
import com.refood.tastie.presentation.register.RegisterViewModel
import com.refood.tastie.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


object AppModules {

    private val networkModule = module {
        single<TastieApiService> { TastieApiService.invoke() }
    }

    private val firebaseModule = module {
        factory<FirebaseAuth> { FirebaseAuth.getInstance() }
    }

    private val localModule = module {
        single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
        single<CartDao> { get<AppDatabase>().cartDao() }
        single<SharedPreferences> {
            SharedPreferenceUtils.createPreference(
                androidContext(),
                UserPreferenceImpl.PREF_NAME
            )
        }
        single<UserPreference> { UserPreferenceImpl(get()) }
    }
    private val datasource = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<CategoryDataSource> { CategoryApiDataSource(get()) }
        single<MenuDataSource> { MenuApiDataSource(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repository = module {
        single<CartRepository> { CartRepositoryImpl(get()) }
        single<CategoryRepository> { CategoryRepositoryImpl(get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModel { params ->
            //assisted injection
            DetailMenuViewModel(
                extras = params.get(),
                cartRepository = get()
            )
        }
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::ProfileViewModel)
    }

    val modules = listOf(
        networkModule,
        localModule,
        datasource,
        repository,
        firebaseModule,
        viewModelModule
    )

}