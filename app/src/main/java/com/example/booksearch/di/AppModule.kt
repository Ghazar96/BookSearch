package com.example.booksearch.di

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.example.booksearch.SettingsSaveService
import com.example.booksearch.SettingsSharedPreferencesSaveService
import com.example.booksearch.SharedViewModel
import com.example.booksearch.booksPage.BooksViewModel
import com.example.booksearch.booksPage.GetBooksRepo
import com.example.booksearch.booksPage.GetBooksRepoImpl
import com.example.booksearch.mappers.NetworkMapper
import com.example.booksearch.network.services.GetBookRetrofitNetworkService
import com.example.booksearch.network.services.GetBooksNetworkService
import com.example.booksearch.network.services.NetworkServiceApi
import com.example.booksearch.settingsFragment.GetSettingsRepo
import com.example.booksearch.settingsFragment.GetSettingsRepoImpl
import com.example.booksearch.settingsFragment.SettingsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun appModule(sharedPreferences: SharedPreferences) = module {
    factory<GetBooksNetworkService> {
        GetBookRetrofitNetworkService(
            Retrofit.Builder()
                .callFactory(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.googleapis.com/books/v1/")
                .build().create(NetworkServiceApi::class.java),
            mapper = NetworkMapper()
        )
    }

    factory<GetBooksRepo> {
        GetBooksRepoImpl(getBooksNetworkService = get(), settingsSaveService = get())
    }

    factory<GetSettingsRepo> {
        GetSettingsRepoImpl(settingsSaveService = get())
    }

    factory<SettingsSaveService> {
        SettingsSharedPreferencesSaveService(sharedPreferences)
    }

    viewModel { (handle: SavedStateHandle) ->
        BooksViewModel(
            getBooksRepo = get(),
            settingsRepo = get()
        )
    }

    viewModel { (handle: SavedStateHandle) ->
        SettingsViewModel(
            repo = get()
        )
    }

    viewModel {
        SharedViewModel()
    }
}
