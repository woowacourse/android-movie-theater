package woowacourse.movie.view.movies

import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.model.ScreeningInfo
import woowacourse.movie.view.movies.model.UiModel

interface MovieListContract {
    interface View {
        fun showMovieList(movieList: List<UiModel>)

        fun showTheaterBottomSheet(
            movieId: Int,
            theaters: Theaters,
        )

        fun moveToBooking(screening: ScreeningInfo)
    }

    interface Presenter {
        fun loadUiData()

        fun loadTheaters(movieId: Int)

        fun loadMovieScreening(
            movieId: Int,
            selectedTheater: Theater,
        )
    }
}
