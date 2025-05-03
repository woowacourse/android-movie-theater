package woowacourse.movie.view.movie

import woowacourse.movie.view.model.MainItem
import woowacourse.movie.view.model.MovieUiModel

interface MovieContract {
    interface View {
        fun showMovies(items: List<MainItem>)

        fun showTheaterInfo(movie: MovieUiModel)
    }

    interface Presenter {
        fun fetchMovies()

        fun reservationSelected(movie: MovieUiModel)
    }
}
