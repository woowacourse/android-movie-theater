package woowacourse.movie.application

import android.app.Application
import woowacourse.movie.sharedpreference.SharedPreferences

class MovieApp: Application() {
    companion object {
        lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPreferences(applicationContext)
        super.onCreate()
    }
}
