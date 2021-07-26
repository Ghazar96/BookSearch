package com.example.booksearch

import android.content.SharedPreferences

interface SettingsSaveService {
    fun updateSettings(settingsType: SettingsType)

    fun getSettingsType(): SettingsType
}

class SettingsSharedPreferencesSaveService(private val sharedPreferences: SharedPreferences) :
    SettingsSaveService {

    override fun updateSettings(settingsType: SettingsType) {
        sharedPreferences.edit().putString(SETTING_KEY, settingsType.toString()).apply()
    }


    override fun getSettingsType(): SettingsType {
        return when (sharedPreferences.getString(SETTING_KEY, null)) {
            "По автору" -> SettingsType.AUTHOR
            "По названию" -> SettingsType.NAME
            "По жанру" -> SettingsType.GENRE
            "По издателю" -> SettingsType.PUBLISHER
            else -> SettingsType.ALL
        }
    }

    companion object {
        const val SETTING_KEY = "setting_key"
    }
}

enum class SettingsType {
    ALL {
        override fun toString(): String {
            return "Поиск по всему"
        }

        override fun getRequestKey(): String {
            return ""
        }
    },
    AUTHOR {
        override fun toString(): String {
            return "По автору"
        }

        override fun getRequestKey(): String {
            return "inauthor"
        }
    },
    NAME {
        override fun toString(): String {
            return "По названию"
        }

        override fun getRequestKey(): String {
            return "intitle"
        }
    },
    GENRE {
        override fun toString(): String {
            return "По жанру"
        }

        override fun getRequestKey(): String {
            return "subject"
        }
    },
    PUBLISHER {
        override fun toString(): String {
            return "По издателю"
        }

        override fun getRequestKey(): String {
            return "inpublisher"
        }
    };

    abstract override fun toString(): String
    abstract fun getRequestKey(): String
}
