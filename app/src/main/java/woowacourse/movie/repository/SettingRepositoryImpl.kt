package woowacourse.movie.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object SettingRepositoryImpl : SettingRepository {
    private lateinit var sharedPreferences: SharedPreferences
    private const val PUSH_ALARM_KEY = "pushAlarm"
    private const val SETTING = "settings"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SETTING, AppCompatActivity.MODE_PRIVATE)
    }

    override fun setNotificationState(isChecked: Boolean) {
        sharedPreferences.edit().putBoolean(PUSH_ALARM_KEY, isChecked).apply()
    }

    override fun getNotificationState(): Boolean = sharedPreferences.getBoolean(PUSH_ALARM_KEY, false)
}
