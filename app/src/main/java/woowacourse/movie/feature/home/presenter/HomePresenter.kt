package woowacourse.movie.feature.home.presenter

import woowacourse.movie.data.ContentService.getAllContents
import woowacourse.movie.data.ContentService.getMovieScreenings
import woowacourse.movie.feature.home.contract.HomeContract
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.MovieUiModel

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun prepareContents() {
        view.showContents(getAllContents().map { it.toUi() })
    }

    override fun selectMovieForBooking(movie: MovieUiModel) {
        view.showTheaters(getMovieScreenings(movie.title).toUi())
    }
}
