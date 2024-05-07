package woowacourse.movie.presentation.homefragments.movieList

import woowacourse.movie.model.Movie

interface MovieListContract {
    interface View {
        fun displayMovies(movies: List<Movie>)

        fun ticketingButtonClickListener(movieId: Long)
    }

    interface Presenter {
        fun loadMovies()
    }
}
