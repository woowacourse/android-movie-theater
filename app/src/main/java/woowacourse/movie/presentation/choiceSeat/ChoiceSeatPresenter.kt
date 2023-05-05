package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.model.data.storage.MovieStorage
import woowacourse.movie.model.data.storage.SettingStorage

class ChoiceSeatPresenter(
    override val view: ChoiceSeatContract.View,
    private val settingStorage: SettingStorage,
    private val movieStorage: MovieStorage
) : ChoiceSeatContract.Presenter {

    override fun getMovieById(movieId: Long): Movie = movieStorage.getMovieById(movieId)

    override fun getNotificationSettings() = settingStorage.getNotificationSettings()
}
