package woowacourse.movie.view.home.movies

import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.view.home.movies.model.MovieRvItem
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.mapper.toItem

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieStore: MovieStore,
    theaterStore: TheaterStore,
) : MovieListContract.Presenter {
    private val theaters = theaterStore.theaters()

    override fun loadTheaters(
        movieId: Int,
        formatter: String,
    ) {
        val bookingAbleTheater =
            theaters.bookingAbleTheater(movieId).map { it.toItem(movieId, formatter) }

        view.showTheaterBottomSheet(movieId, bookingAbleTheater)
    }

    override fun loadMovieScreening(
        movieId: Int,
        theaterName: String,
    ) {
        val screeningTimes = theaters.selectedMovieScreeningTimes(movieId, theaterName)

        val screeningInfo = ScreeningInfo(movieId, theaterName, screeningTimes)

        view.moveToBooking(screeningInfo)
    }

    override fun loadUiData() {
        val movieRvItems = mutableListOf<MovieRvItem>()
        movieStore.getAll().forEachIndexed { index, movie ->
            movieRvItems.add(movie.toItem())
            if ((index + 1) % AD_DIVIDE_STANDARD == 0) {
                movieRvItems.add(Advertisement().toItem())
            }
        }
        view.showMovieList(movieRvItems)
    }

    companion object {
        fun initialize(view: MovieListContract.View): MovieListContract.Presenter {
            val movieStore = MovieStore()
            val theaterStore = TheaterStore(movieStore)
            return MovieListPresenter(view, movieStore, theaterStore)
        }

        private const val AD_DIVIDE_STANDARD = 3
    }
}
