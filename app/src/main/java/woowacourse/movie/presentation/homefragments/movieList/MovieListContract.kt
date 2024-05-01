package woowacourse.movie.presentation.homefragments.movieList

import woowacourse.movie.model.Movie

interface MovieListContract {
    interface View {
        fun displayMovies(movies: List<Movie>)
    }

    interface Presenter {
        fun loadMovies()
    }
}
