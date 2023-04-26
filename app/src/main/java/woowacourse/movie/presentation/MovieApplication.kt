package woowacourse.movie.presentation

import android.app.Application
import com.woowacourse.data.local.Preferences

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(applicationContext)
    }

    companion object {
        internal lateinit var preferences: Preferences
    }
}
