package com.woowacourse.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PreferenceEditor(context: Context) {
    private val sharedPreference = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    companion object {
        private const val PREF_NAME = "settings"
    }
}
