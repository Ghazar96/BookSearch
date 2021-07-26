package com.example.booksearch.network.services

interface GetBooksNetworkService {
    suspend fun getBooks(name: String, performingKey: String): GetBookListRequestState
    suspend fun getBook(id: String): GetBookRequestState

}
