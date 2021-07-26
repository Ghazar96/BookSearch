package com.example.booksearch.network.services

import com.example.booksearch.network.pojos.BookListRequestPojo
import com.example.booksearch.network.pojos.BookPojo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkServiceApi {
    @GET("volumes")
    suspend fun getBooks(@Query("q") name: String): BookListRequestPojo

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): BookPojo
}