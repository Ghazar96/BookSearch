package com.example.booksearch.settingsFragment

import com.example.booksearch.SettingsSaveService
import com.example.booksearch.SettingsType

interface GetSettingsRepo {
    fun getSettings(): SettingsList

    fun updateSelectedSettings(data: SettingsData)

    fun getSettingsType(): SettingsType
}

class GetSettingsRepoImpl(val settingsSaveService: SettingsSaveService) : GetSettingsRepo {
    override fun getSettings(): SettingsList {
        val selectedType = settingsSaveService.getSettingsType()
        return SettingsList(
            createSettingsData(selectedType)
        )
    }

    override fun updateSelectedSettings(data: SettingsData) {
        settingsSaveService.updateSettings(data.type)
    }

    private fun createSettingsData(selectedType: SettingsType): List<SettingsData> {
        return arrayListOf(
            SettingsData(
                SettingsType.ALL,
                SettingsType.ALL == selectedType
            ),
            SettingsData(
                SettingsType.PUBLISHER,
                SettingsType.PUBLISHER == selectedType
            ),
            SettingsData(
                SettingsType.AUTHOR,
                SettingsType.AUTHOR == selectedType
            ),
            SettingsData(
                SettingsType.NAME,
                SettingsType.NAME == selectedType
            ),
            SettingsData(
                SettingsType.GENRE,
                SettingsType.GENRE == selectedType
            )
        )
    }

    override fun getSettingsType(): SettingsType {
        return settingsSaveService.getSettingsType()
    }
}

data class SettingsList(val settings: List<SettingsData>)

data class SettingsData(val type: SettingsType, var selected: Boolean = false)