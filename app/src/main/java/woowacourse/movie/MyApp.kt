package woowacourse.movie

import android.app.Application
import android.content.Context

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MyApp
        val applicationContext: Context get() = instance.applicationContext
    }
}
