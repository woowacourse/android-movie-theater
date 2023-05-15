package woowacourse.movie.global

import android.app.Application
import woowacourse.movie.data.preference.AlarmSettingPreference

class MyApplication : Application() {
    override fun onCreate() {
        AlarmSettingPreference.init(this)
        super.onCreate()
    }
}
