package com.example.booksearch.settingsFragment

import androidx.lifecycle.ViewModel

class SettingsViewModel(private val repo: GetSettingsRepo) :
    ViewModel() {

    fun getSettingsList(): SettingsList {
        return repo.getSettings()
    }

    fun updateSelectedSettings(data: SettingsData) {
        return repo.updateSelectedSettings(data)
    }
}