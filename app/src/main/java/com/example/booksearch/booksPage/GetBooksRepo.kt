package com.example.booksearch.booksPage

import com.example.booksearch.SettingsSaveService
import com.example.booksearch.network.services.GetBookListRequestState
import com.example.booksearch.network.services.GetBookRequestState
import com.example.booksearch.network.services.GetBooksNetworkService

interface GetBooksRepo {
    suspend fun getBooks(name: String): GetBookListRequestState
    suspend fun getBook(id: String): GetBookRequestState
}

class GetBooksRepoImpl(
    val getBooksNetworkService: GetBooksNetworkService,
    private val settingsSaveService: SettingsSaveService
) : GetBooksRepo {
    override suspend fun getBooks(name: String): GetBookListRequestState {
        return getBooksNetworkService.getBooks(
            name,
            settingsSaveService.getSettingsType().getRequestKey()
        )
    }

    override suspend fun getBook(id: String): GetBookRequestState {
        return getBooksNetworkService.getBook(id)
    }
}
