package woowacourse.movie.view.movie

import woowacourse.movie.view.model.MovieUiModel

interface MovieContract {
    interface View {
        fun showMovies(movies: List<MovieUiModel>)

        fun showTheaterInfo(movie: MovieUiModel)
    }

    interface Presenter {
        fun fetchMovies()

        fun reservationSelected(movie: MovieUiModel)
    }
}
