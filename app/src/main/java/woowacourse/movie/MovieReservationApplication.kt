package woowacourse.movie

import android.app.Application
import woowacourse.movie.database.SettingPreferencesManager

class MovieReservationApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SettingPreferencesManager.init(this)
    }
}
