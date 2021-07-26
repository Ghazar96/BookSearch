package com.example.booksearch.booksPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearch.R
import com.example.booksearch.SharedViewModel
import com.example.booksearch.network.services.BookData
import com.example.booksearch.network.services.BookRequestData
import kotlinx.android.synthetic.main.fragment_books.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class BooksFragment : Fragment() {
    private val bookViewModel: BooksViewModel by stateViewModel()

    private val sharedViewModel by sharedViewModel<SharedViewModel>()

    private var bookAdapter: BookAdapter? = null

    private var searchView: SearchView? = null
    private var progressBar: ProgressBar? = null
    private var settingsButton: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        searchView = view?.findViewById(R.id.searchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() != false) {
                    return false
                }
                progressBar?.visibility = View.VISIBLE
                bookViewModel.getBooks(newText)
                return true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsButton = view.findViewById(R.id.settingsButton)
        progressBar = view.findViewById(R.id.loading)

        updateBadge(!bookViewModel.isSelectedDefaultSettings())

        val listView = view.findViewById<RecyclerView>(R.id.listView)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        listView?.layoutManager = llm
        bookAdapter = BookAdapter(ArrayList())

        listView?.adapter = bookAdapter

        initObservers()

        settingsButton?.setOnClickListener {
            sharedViewModel.openSettingsPage()
        }
    }

    private fun initObservers() {
        bookViewModel.showErrorLiveData.observe(viewLifecycleOwner, { errorText ->
            handleError(errorText)
        })

        bookViewModel.bookListReadyLiveData.observe(viewLifecycleOwner, {
            progressBar?.visibility = View.GONE
            handleBooksDataComplete(it)
        })

        bookViewModel.bookReadyLiveData.observe(viewLifecycleOwner, {
            addImageUrlToListView(it)
        })

        sharedViewModel.showBadgeLiveData.observe(viewLifecycleOwner, { show ->
            updateBadge(show)
        })

        sharedViewModel.backToMainPageLiveData.observe(viewLifecycleOwner, {
            val search = searchView?.query?.toString()
            if (search?.isEmpty() != false) {
                return@observe
            }
            bookViewModel.updateBooksIfNeed(search)
        })
    }

    private fun updateBadge(show: Boolean) {
        val resource = if (show) R.drawable.ic_parameters_active else R.drawable.ic_filled
        settingsButton?.setImageResource(resource)
    }

    private fun handleError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun addImageUrlToListView(bookData: BookData) {
        bookData.imageUrl?.let {
            bookAdapter?.changeBookDataImageUrl(bookData.id, it)
        }
    }

    private fun handleBooksDataComplete(booksData: BookRequestData) {
        val noListTextView = view?.findViewById<TextView>(R.id.noListTextView)
        if (booksData.books.isEmpty()) {
            noListTextView?.visibility = View.VISIBLE
        } else {
            noListTextView?.visibility = View.GONE
        }

        booksData.books.forEach { bookData ->
            bookViewModel.getBook(bookData.id)
        }
        bookAdapter?.setItems(booksData.books)
    }
}
