package woowacourse.movie.view.home.movies

import woowacourse.movie.view.home.movies.model.MovieRvItem
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.home.movies.model.TheaterRvItem

interface MovieListContract {
    interface View {
        fun showMovieList(movieList: List<MovieRvItem>)

        fun showTheaterBottomSheet(
            movieId: Int,
            theaters: List<TheaterRvItem.TheaterItem>,
        )

        fun moveToBooking(screening: ScreeningInfo)
    }

    interface Presenter {
        fun loadUiData()

        fun loadTheaters(
            movieId: Int,
            formatter: String,
        )

        fun loadMovieScreening(
            movieId: Int,
            theaterName: String,
        )
    }
}
