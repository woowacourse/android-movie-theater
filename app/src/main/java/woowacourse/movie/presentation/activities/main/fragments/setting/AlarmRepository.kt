package woowacourse.movie.presentation.activities.main.fragments.setting

import android.content.SharedPreferences
import com.woowacourse.data.local.PreferenceManager.setBoolean

class AlarmRepository(
    private val sharedPreferences: SharedPreferences,
) {
    fun setBoolean(value: Boolean) {
        sharedPreferences.setBoolean(KEY_ALLOW, value)
    }

    fun getBoolean(defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(KEY_ALLOW, defaultValue)
    }

    companion object {
        const val KEY_ALLOW = "push_allow_key"
        private const val DEFAULT_VALUE = true
    }
}
