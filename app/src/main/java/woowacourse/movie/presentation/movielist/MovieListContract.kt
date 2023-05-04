package woowacourse.movie.presentation.movielist

import woowacourse.movie.presentation.model.MovieModel

interface MovieListContract {
    interface View {
        val presenter: Presenter
        fun setMoviesAdapter(movies: List<MovieModel>)
    }

    interface Presenter {
        fun requestMovies()
    }
}
