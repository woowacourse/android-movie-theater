package woowacourse.movie.global

import android.app.Application
import woowacourse.movie.data.AlarmSettingRepositoryImpl

class MyApplication : Application() {
    override fun onCreate() {
        AlarmSettingRepositoryImpl.init(this)
        super.onCreate()
    }
}
