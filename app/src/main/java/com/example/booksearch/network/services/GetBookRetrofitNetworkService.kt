package com.example.booksearch.network.services

import com.example.booksearch.mappers.NetworkMapper

class GetBookRetrofitNetworkService(
    private val networkServiceApi: NetworkServiceApi,
    val mapper: NetworkMapper
) :
    GetBooksNetworkService {
    override suspend fun getBooks(name: String, performingKey: String): GetBookListRequestState {
        return try {
            mapper.bookPojoToGetBookListRequestStateMapper.map(
                networkServiceApi.getBooks(
                    performingKey + name
                )
            )
        } catch (e: Exception) {
            GetBookListRequestState.Error("Cannot get books list")
        }
    }

    override suspend fun getBook(id: String): GetBookRequestState {
        return try {
            mapper.bookPojoToGetBookRequestStateMapper.map(networkServiceApi.getBook(id))
        } catch (e: Exception) {
            GetBookRequestState.Error("Cannot get book data")
        }
    }
}
