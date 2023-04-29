package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtil {
    fun getBooleanValue(context: Context, key: String, default: Boolean): Boolean {
        val sharedPreference = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(key, default)
    }

    fun setBooleanValue(context: Context, key: String, value: Boolean) {
        val sharedPreference = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean(key, value).apply()
    }
}
