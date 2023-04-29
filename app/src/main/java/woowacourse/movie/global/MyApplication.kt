package woowacourse.movie.global

import android.app.Application
import woowacourse.movie.PreferenceUtil

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}
