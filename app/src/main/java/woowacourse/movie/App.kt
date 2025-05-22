package woowacourse.movie

import android.app.Application
import woowacourse.movie.db.ReservationInfoDatabase
import woowacourse.movie.sharedPreference.SettingSharedPreferenceManager

class App : Application() {
    lateinit var preferenceManager: SettingSharedPreferenceManager
    val database by lazy { ReservationInfoDatabase.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        _instance = this
        preferenceManager = SettingSharedPreferenceManager(applicationContext)
    }

    companion object {
        private var _instance: App? = null
        val instance get() = _instance!!
    }
}
