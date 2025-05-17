package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.SettingPreference
import woowacourse.movie.data.database.MovieDatabase

class MovieApplication : Application() {
    init {
        val settingRepository = SettingPreference(applicationContext)
        RepositoryProvider.initSettingRepository(settingRepository)

        val movieDatabase = MovieDatabase.getDatabase(applicationContext)
        RepositoryProvider.initMovieDatabase(movieDatabase)
    }
}
