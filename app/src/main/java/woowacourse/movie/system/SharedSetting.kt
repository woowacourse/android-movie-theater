package woowacourse.movie.system

import android.content.Context
import android.content.SharedPreferences

class SharedSetting(private val context: Context) : Setting {
    override fun getValue(key: String, default: Boolean): Boolean {
        val sharedPreference = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(key, default)
    }

    override fun setValue(key: String, value: Boolean) {
        val sharedPreference = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean(key, value).apply()
    }
}
