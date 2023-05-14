package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY

class SharedPreferenceDataRepository(context: Context) : DataRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SETTING_PUSH_ALARM_SWITCH_KEY, Context.MODE_PRIVATE
    )

    override fun getBooleanValue(default: Boolean): Boolean {
        return sharedPreferences.getBoolean(SETTING_PUSH_ALARM_SWITCH_KEY, default)
    }

    override fun setBooleanValue(value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(SETTING_PUSH_ALARM_SWITCH_KEY, value).apply()
    }
}
