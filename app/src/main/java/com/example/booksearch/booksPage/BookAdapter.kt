package com.example.booksearch.booksPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksearch.R
import com.example.booksearch.network.services.BookData

class BookAdapter(
    private val items: List<BookData>
) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    fun setItems(items: List<BookData>) {
        (this.items as? ArrayList)?.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun changeBookDataImageUrl(id: String, url: String) {
        var pos = -1
        items.forEach { bookData ->
            if (bookData.id == id) {
                bookData.imageUrl = url
                pos = items.indexOf(bookData)
            }
        }
        if (pos >= 0) {
            notifyItemChanged(pos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.book_view_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item: BookData = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(data: BookData) {
            val nameTextView = itemView.findViewById<TextView>(R.id.bookName)
            val authorTextView = itemView.findViewById<TextView>(R.id.authorName)
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)

            nameTextView.text = data.title
            authorTextView.text = getAuthorsText(data.authors)
            data.imageUrl?.let {url->
                Glide.with(itemView.context).load(url).placeholder(R.drawable.ic_img_image).dontAnimate().into(imageView);
            } ?: imageView.setImageResource(R.drawable.ic_img_image)
        }

        private fun getAuthorsText(authors: List<String>?): String {
            var result = ""

            authors?.forEach { author ->
                result = result.plus("$author, ")
            }

            return result
        }
    }
}
