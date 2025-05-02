package woowacourse.movie.view.home.movies

import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.home.movies.model.Item
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.mapper.toItem

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieStore: MovieStore,
    theaterStore: TheaterStore,
) : MovieListContract.Presenter {
    init {
        loadUiData()
    }

    private val theaters = theaterStore.createTheaters()

    override fun loadTheaters(movieId: Int) {
        val bookingAbleTheater = theaters.bookingAbleTheater(movieId)
        view.showTheaterBottomSheet(movieId, bookingAbleTheater)
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

    private fun loadUiData() {
        val items = mutableListOf<Item>()
        movieStore.getAll().forEachIndexed { index, movie ->
            items.add(movie.toItem())
            if ((index + 1) % AD_DIVIDE_STANDARD == 0) {
                items.add(Advertisement().toItem())
            }
        }
        view.showMovieList(items)
    }

    companion object {
        private const val AD_DIVIDE_STANDARD = 3
    }
}
