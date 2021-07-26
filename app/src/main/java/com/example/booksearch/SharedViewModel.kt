package com.example.booksearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var _openSettingsPageLiveData: MutableLiveData<Unit> = MutableLiveData()
    var openSettingsPageLiveData: LiveData<Unit> = _openSettingsPageLiveData

    private var _backToMainPageLiveData: MutableLiveData<Unit> = MutableLiveData()
    var backToMainPageLiveData: LiveData<Unit> = _backToMainPageLiveData

    private var _showBadgeLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var showBadgeLiveData: LiveData<Boolean> = _showBadgeLiveData

    fun openSettingsPage() {
        _openSettingsPageLiveData.value = Unit
    }

    fun backToMainPage() {
        _backToMainPageLiveData.value = Unit
    }

    fun showOrHideBadge(show: Boolean) {
        _showBadgeLiveData.value = show
    }
}