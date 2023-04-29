package woowacourse.movie.presentation

import android.app.Application
import com.woowacourse.data.local.PreferenceEditor

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        preferenceEditor = PreferenceEditor(applicationContext)
    }

    companion object {
        internal lateinit var preferenceEditor: PreferenceEditor
    }
}
