package woowacourse.movie

import android.app.Application

class MovieReservationApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SettingPreferencesManager.init(this)
    }
}
