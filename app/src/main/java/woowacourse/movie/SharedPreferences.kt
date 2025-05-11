package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferences {
    companion object {
        private var notification: SharedPreferences? = null
        private const val KEY_IS_NOTIFICATION: String = "isNotification"
        private const val FILE_LOCATED: String = "notification"

        fun getData(context: Context): Boolean {
            return getPreference(context).getBoolean(KEY_IS_NOTIFICATION, false)
        }

        fun saveData(context: Context, isNotification: Boolean) {
            getPreference(context).edit() {
                putBoolean(KEY_IS_NOTIFICATION, isNotification)
            }
        }

        private fun getPreference(context: Context): SharedPreferences {
            return notification ?: context.getSharedPreferences(
                FILE_LOCATED, Context.MODE_PRIVATE
            ).also { notification = it }
        }

    }
}
