package woowacourse.movie.global

import android.app.Application
import woowacourse.movie.data.AlarmSettingRepository

class MyApplication : Application() {
    override fun onCreate() {
        AlarmSettingRepository.init(this)
        super.onCreate()
    }
}
