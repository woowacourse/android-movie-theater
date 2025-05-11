package woowacourse.movie

import android.app.Application
import java.util.TimeZone

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

    companion object {
        private var _instance: MovieApplication? = null
        private const val APPLICATION_ERROR = "[ERROR] 어플리케이션이 올바르게 실행되지 않았습니다."

        val instance: MovieApplication
            get() = _instance ?: throw IllegalArgumentException(APPLICATION_ERROR)
    }
}
