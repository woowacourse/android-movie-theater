package woowacourse.movie.global

import android.app.Application
import woowacourse.movie.data.AlarmSettingRepository
import woowacourse.movie.data.NotificationIdRepository

class MyApplication : Application() {
    override fun onCreate() {
        AlarmSettingRepository.init(this)
        NotificationIdRepository.init(this)
        super.onCreate()
    }
}
