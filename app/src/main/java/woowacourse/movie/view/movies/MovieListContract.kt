package woowacourse.movie.view.movies

import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.model.UiModel

interface MovieListContract {
    interface View {
        fun showMovieList(movieList: List<UiModel>)

        fun showTheaterBottomSheet(
            movieId: Int,
            theaters: Theaters,
        )
    }

    interface Presenter {
        fun loadUiData()

        fun loadTheaters(movieId: Int)
    }
}
