package woowacourse.movie.view.movie

import woowacourse.movie.view.model.MovieListItem
import woowacourse.movie.view.model.MovieUiModel

interface MovieContract {
    interface View {
        fun showMovies(items: List<MovieListItem>)

        fun showTheaterInfo(movie: MovieUiModel)
    }

    interface Presenter {
        fun fetchMovies()

        fun reservationSelected(movie: MovieUiModel)
    }
}
