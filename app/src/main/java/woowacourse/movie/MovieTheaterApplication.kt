package woowacourse.movie

import android.app.Application
import android.content.IntentFilter
import woowacourse.movie.broadcastreceiver.AlarmReceiver

class MovieTheaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initAlarmReceiver()
    }

    private fun initAlarmReceiver() {
        val alarmReceiver = AlarmReceiver()
        registerReceiver(alarmReceiver, IntentFilter())
    }
}
