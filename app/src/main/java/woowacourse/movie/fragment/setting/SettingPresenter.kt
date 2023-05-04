package woowacourse.movie.fragment.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class SettingPresenter(context: Context): SettingContract.Presenter {
    private val sharedPreference = context.getSharedPreferences(SETTING, AppCompatActivity.MODE_PRIVATE)

    override fun getNotificationState(): Boolean {
        return sharedPreference.getBoolean(PUSH_ALARM_KEY, false)
    }

    override fun setSwitchState(isChecked: Boolean) {
        when(isChecked) {
            true -> sharedPreference.edit().putBoolean(PUSH_ALARM_KEY, true).apply()
            false -> sharedPreference.edit().putBoolean(PUSH_ALARM_KEY, false).apply()
        }
    }

    companion object {
        private const val PUSH_ALARM_KEY = "pushAlarm"
        private const val SETTING = "settings"
    }
}
