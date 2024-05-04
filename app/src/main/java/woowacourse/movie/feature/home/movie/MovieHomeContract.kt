package woowacourse.movie.feature.home.movie

import woowacourse.movie.model.Movie

interface MovieHomeContract {
    interface View {
        fun displayMovies(movies: List<Movie>)

        fun displayTheaterSelectionDialog(id: Long)
    }

    interface Presenter {
        fun loadMovies()
    }
}
