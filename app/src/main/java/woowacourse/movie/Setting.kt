package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

object Setting {
    fun getSettingValue(context: Context, key: String, default: Boolean = false): Boolean {
        val sharedPreference = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(key, default)
    }

    fun setSettingValue(context: Context, key: String, value: Boolean) {
        val sharedPreference = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean(key, value).apply()
    }
}
