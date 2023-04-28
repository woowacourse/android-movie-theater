package com.woowacourse.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.woowacourse.data.DataStore

class LocalDataStore(context: Context) : DataStore {
    private val sharedPreference = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    companion object {
        private const val PREF_NAME = "settings"
    }
}
