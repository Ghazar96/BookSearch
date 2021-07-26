package com.example.booksearch.network.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BookListRequestPojo(
    @SerializedName("kind")
    @Expose
    val kind: String,
    @SerializedName("items")
    @Expose
    val items: List<BookPojo>? = null,
    @SerializedName("totalItems")
    @Expose
    val totalItems: Int
)

data class BookPojo(
    @SerializedName("kind")
    @Expose
    val kind: String,
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("etag")
    @Expose
    val etag: String,
    @SerializedName("selfLink")
    @Expose
    val selfLink: String,
    @SerializedName("volumeInfo")
    @Expose
    val volumePojo: VolumePojo
)

data class ImageLinks(
    @SerializedName("smallThumbnail")
    @Expose
    val smallThumbnail: String,
    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String,
    @SerializedName("small")
    @Expose
    val small: String,
    @SerializedName("medium")
    @Expose
    val medium: String,
    @SerializedName("large")
    @Expose
    val large: String,
    @SerializedName("extraLarge")
    @Expose
    val extraLarge: String
)

data class VolumePojo(
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("authors")
    @Expose
    val authors: List<String>? = null,
    @SerializedName("imageLinks")
    @Expose
    val imageLinks: ImageLinks? = null
)
