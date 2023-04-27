package woowacourse.movie.movie

import android.content.Context
import android.content.SharedPreferences

class SettingPreference(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("booking_movie", Context.MODE_PRIVATE)

    var setting: Boolean
        get() = sharedPreferences.getBoolean("setting", false)
        set(value) = sharedPreferences.edit().putBoolean("setting", value).apply()

    companion object {
    }
}
