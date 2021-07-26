package com.example.booksearch.booksPage

import androidx.lifecycle.*
import com.example.booksearch.SettingsType
import com.example.booksearch.isDefault
import com.example.booksearch.network.services.BookData
import com.example.booksearch.network.services.BookRequestData
import com.example.booksearch.network.services.GetBookListRequestState
import com.example.booksearch.network.services.GetBookRequestState
import com.example.booksearch.settingsFragment.GetSettingsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksViewModel(
    val getBooksRepo: GetBooksRepo,
    private val settingsRepo: GetSettingsRepo
) : ViewModel() {

    private var _showErrorLiveData: MutableLiveData<String> = MutableLiveData()
    var showErrorLiveData: LiveData<String> = _showErrorLiveData

    private var _bookListReadyLiveData: MutableLiveData<BookRequestData> = MutableLiveData()
    var bookListReadyLiveData: LiveData<BookRequestData> = _bookListReadyLiveData

    private var _bookReadyLiveData: MutableLiveData<BookData> = MutableLiveData()
    var bookReadyLiveData: LiveData<BookData> = _bookReadyLiveData

    private var settingsType: SettingsType = settingsRepo.getSettingsType()

    fun getBooks(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val requestState = getBooksRepo.getBooks(name)

            if (requestState is GetBookListRequestState.Success) {
                _bookListReadyLiveData.postValue(requestState.bookRequestData)
            } else if (requestState is GetBookListRequestState.Error) {
                _showErrorLiveData.postValue(requestState.message)
            }
        }
    }

    fun getBook(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val requestState = getBooksRepo.getBook(id)

            if (requestState is GetBookRequestState.Success) {
                _bookReadyLiveData.postValue(requestState.bookRequestData)
            } else if (requestState is GetBookRequestState.Error) {
                _showErrorLiveData.postValue(requestState.message)
            }
        }
    }

    fun updateBooksIfNeed(name: String) {
        val currentState = settingsRepo.getSettingsType()
        if (settingsType != currentState) {
            settingsType = currentState
            getBooks(name)
        }
    }

    fun isSelectedDefaultSettings(): Boolean {
        return settingsType.isDefault()
    }
}
