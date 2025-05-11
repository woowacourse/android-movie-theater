package woowacourse.movie

import android.app.Application

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MovieApplication

        fun getInstance(): MovieApplication = instance
    }
}
