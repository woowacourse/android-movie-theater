package woowacourse.movie

import woowacourse.movie.data.SettingRepository
import woowacourse.movie.data.database.MovieDatabase

object RepositoryProvider {
    private var _settingRepository: SettingRepository? = null
    val settingRepository get() = _settingRepository!!

    private var _movieDatabase: MovieDatabase? = null
    val movieDatabase get() = _movieDatabase!!

    fun initSettingRepository(settingRepository: SettingRepository) {
        _settingRepository = settingRepository
    }

    fun initMovieDatabase(movieDatabase: MovieDatabase) {
        _movieDatabase = movieDatabase
    }
}
