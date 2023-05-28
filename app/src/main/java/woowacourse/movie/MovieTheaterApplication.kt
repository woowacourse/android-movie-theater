package woowacourse.movie

import android.app.Application
import android.content.IntentFilter
import woowacourse.movie.broadcastreceiver.AlarmReceiver
import woowacourse.movie.model.data.remote.DummyMovieStorage

class MovieTheaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initAlarmReceiver()
    }

    private fun initAlarmReceiver() {
        val alarmReceiver = AlarmReceiver(DummyMovieStorage())
        registerReceiver(alarmReceiver, IntentFilter())
    }
}
