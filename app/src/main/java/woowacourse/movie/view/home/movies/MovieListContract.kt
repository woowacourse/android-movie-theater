package woowacourse.movie.view.home.movies

import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.home.movies.model.MovieRvItem
import woowacourse.movie.view.home.movies.model.ScreeningInfo

interface MovieListContract {
    interface View {
        fun showMovieList(movieList: List<MovieRvItem>)

        fun showTheaterBottomSheet(
            movieId: Int,
            theaters: Theaters,
        )

        fun moveToBooking(screening: ScreeningInfo)
    }

    interface Presenter {
        fun loadTheaters(movieId: Int)

        fun loadMovieScreening(
            movieId: Int,
            selectedTheater: Theater,
        )
    }
}
