package woowacourse.movie

import android.app.Application
import woowacourse.movie.sharedPreference.SettingSharedPreferenceManager

class App : Application() {
    lateinit var preferenceManager: SettingSharedPreferenceManager

    override fun onCreate() {
        super.onCreate()
        preferenceManager = SettingSharedPreferenceManager(applicationContext)
    }
}
