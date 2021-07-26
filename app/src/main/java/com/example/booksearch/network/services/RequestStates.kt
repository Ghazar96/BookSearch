package com.example.booksearch.network.services

sealed class GetBookListRequestState {
    data class Success(val bookRequestData: BookRequestData) : GetBookListRequestState()
    data class Error(val message: String) : GetBookListRequestState()
}

sealed class GetBookRequestState {
    data class Success(val bookRequestData: BookData) : GetBookRequestState()
    data class Error(val message: String) : GetBookRequestState()
}

data class BookRequestData(
    val kind: String,
    val books: List<BookData>,
    val totalItems: Int
)

data class BookData(
    val kind: String,
    val id: String,
    val tag: String,
    val selfLink: String,
    val title: String,
    val authors: List<String>? = null,
    var imageUrl: String? = null
)
