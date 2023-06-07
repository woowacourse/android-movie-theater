package com.woowacourse.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object PreferenceManager {

    private const val PREF_NAME = "settings"

    fun getInstance(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    fun SharedPreferences.setBoolean(key: String, value: Boolean) {
        edit().putBoolean(key, value).apply()
    }
}
