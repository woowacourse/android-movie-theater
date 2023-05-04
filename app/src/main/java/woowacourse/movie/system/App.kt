package woowacourse.movie.system

import android.app.Application
import woowacourse.movie.data.dataSource.LocalDatabase
import woowacourse.movie.data.dataSource.SharedSetting

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedSetting.initWithContext(applicationContext)
        LocalDatabase.initWithContext(applicationContext)
    }
}
