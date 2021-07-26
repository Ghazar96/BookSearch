package com.example.booksearch.mappers

import com.example.booksearch.network.pojos.BookListRequestPojo
import com.example.booksearch.network.pojos.BookPojo
import com.example.booksearch.network.services.BookData
import com.example.booksearch.network.services.BookRequestData
import com.example.booksearch.network.services.GetBookListRequestState
import com.example.booksearch.network.services.GetBookRequestState

class NetworkMapper {

    val bookPojoToGetBookListRequestStateMapper =
        object : Mapper<BookListRequestPojo, GetBookListRequestState> {
            override fun map(s: BookListRequestPojo): GetBookListRequestState {
                return GetBookListRequestState.Success(bookRequestPojoToBookDataMapper.map(s))
            }
        }

    val bookPojoToGetBookRequestStateMapper =
        object : Mapper<BookPojo, GetBookRequestState> {
            override fun map(s: BookPojo): GetBookRequestState {
                return GetBookRequestState.Success(bookPojoToBookDataMapper.map(s))
            }
        }

    val bookRequestPojoToBookDataMapper = object : Mapper<BookListRequestPojo, BookRequestData> {
        override fun map(s: BookListRequestPojo): BookRequestData {
            return BookRequestData(
                kind = s.kind,
                books = bookPojoToBookDataMapper.map(s.items ?: ArrayList()),
                totalItems = s.totalItems
            )
        }
    }

    val bookPojoToBookDataMapper = object : Mapper<BookPojo, BookData> {
        override fun map(s: BookPojo): BookData {
            return BookData(
                kind = s.kind,
                id = s.id,
                tag = s.etag,
                selfLink = s.selfLink,
                title = s.volumePojo.title,
                authors = s.volumePojo.authors,
                imageUrl = s.volumePojo.imageLinks?.thumbnail
            )
        }
    }
}
