package woowacourse.movie.view.home.movies

import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.home.movies.model.UiModel
import woowacourse.movie.view.home.movies.model.toUiModel

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieStore: MovieStore,
    theaterStore: TheaterStore,
) : MovieListContract.Presenter {
    private val theaters = theaterStore.createTheaters()

    override fun loadUiData() {
        val items = mutableListOf<UiModel>()
        movieStore.getAll().forEachIndexed { index, movie ->
            items.add(movie.toUiModel())
            if ((index + 1) % AD_DIVIDE_STANDARD == 0) {
                items.add(Advertisement().toUiModel())
            }
        }
        view.showMovieList(items)
    }

    override fun loadTheaters(movieId: Int) {
        view.showTheaterBottomSheet(movieId, theaters)
    }

    override fun loadMovieScreening(
        movieId: Int,
        selectedTheater: Theater,
    ) {
        val theaterName = selectedTheater.name
        val screeningTimes = selectedTheater.getMovieScreening(movieId)
        val screeningInfo = ScreeningInfo(movieId, theaterName, screeningTimes)

        view.moveToBooking(screeningInfo)
    }

    companion object {
        private const val AD_DIVIDE_STANDARD = 3
    }
}
