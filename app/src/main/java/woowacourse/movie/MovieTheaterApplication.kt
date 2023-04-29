package woowacourse.movie

import android.app.Application
import android.content.IntentFilter
import woowacourse.movie.broadcastreceiver.AlarmReceiver
import woowacourse.movie.data.SettingsData

class MovieTheaterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAlarmReceiver()
        initSettingsPreference()
    }

    private fun initAlarmReceiver() {
        val alarmReceiver = AlarmReceiver()
        registerReceiver(alarmReceiver, IntentFilter())
    }

    private fun initSettingsPreference() {
        SettingsData.init(applicationContext)
    }
}
