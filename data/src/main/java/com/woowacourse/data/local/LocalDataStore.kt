package com.woowacourse.data.local

import android.content.Context
import android.content.SharedPreferences
import com.woowacourse.data.DataStore

class LocalDataStore : DataStore {
    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preference.getBoolean(key, defValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    companion object {
        private const val PREF_NAME = "settings"

        @Volatile
        private var instance: LocalDataStore? = null
        private lateinit var preference: SharedPreferences

        fun getInstance(context: Context): LocalDataStore = instance ?: synchronized(this) {
            instance ?: LocalDataStore().also {
                preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                instance = it
            }
        }
    }
}
