package woowacourse.movie.global

import android.app.Application
import woowacourse.movie.data.AlarmSettingRepository

class MyApplication : Application() {
    companion object {
        lateinit var prefs: AlarmSettingRepository
    }

    override fun onCreate() {
        prefs = AlarmSettingRepository(this)
        super.onCreate()
    }
}
