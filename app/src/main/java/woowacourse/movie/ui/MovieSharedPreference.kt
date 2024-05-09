package woowacourse.movie.ui

import android.content.Context
import android.content.SharedPreferences

class MovieSharedPreference(private val context: Context) {
    private val preferences by lazy { initializePreference() }

    fun setAlarmChecked(isChecked: Boolean) {
        with(preferences.edit()) {
            putBoolean(PREFERENCE_KEY, isChecked)
            apply()
        }
    }

    fun getAlarmChecked(): Boolean {
        return preferences.getBoolean(PREFERENCE_KEY, false)
    }

    private fun initializePreference(): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
            ?: throw IllegalStateException()

    companion object {
        private const val PREFERENCE_KEY = "settings"
    }
}
