package woowacourse.movie

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}