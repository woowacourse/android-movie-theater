package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

object MovieSharedPreferences {
    private const val PREF_NAME_SETTINGS = "settings"
    const val KEY_NOTIFICATION = "notification"

    fun getSettingsSharedPrefs(context: Context): SharedPreferences = context.getSharedPreferences(PREF_NAME_SETTINGS, Context.MODE_PRIVATE)
}
