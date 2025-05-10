package woowacourse.movie

import android.app.Application

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        _instance = this
    }

    companion object {
        private const val NOT_INITIALIZED_MESSAGE = "Application Instance가 아직 초기화되지 않음"

        private var _instance: GlobalApplication? = null
        val instance: GlobalApplication
            get() = requireNotNull(_instance) { NOT_INITIALIZED_MESSAGE }
    }
}
