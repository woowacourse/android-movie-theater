package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.local.datasource.SettingsDataSourceImpl
import woowacourse.movie.domain.datasource.SettingsDataSource

class MovieApplication : Application() {
    val settingDataSource: SettingsDataSource by lazy { SettingsDataSourceImpl.of(applicationContext) }
}
