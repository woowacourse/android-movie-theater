package woowacourse.movie

import android.app.Application
import android.content.IntentFilter
import android.content.SharedPreferences
import woowacourse.movie.broadcastreceiver.AlarmReceiver

class MovieTheaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val alarmReceiver = AlarmReceiver()
        initSettingsPreference()
        registerReceiver(alarmReceiver, IntentFilter())
    }

    private fun initSettingsPreference() {
        settingsPreference = getSharedPreferences(SETTINGS, MODE_PRIVATE)
    }

    companion object {
        private const val SETTINGS = "settings"
        lateinit var settingsPreference: SharedPreferences
    }
}
