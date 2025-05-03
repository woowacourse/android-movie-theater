package woowacourse.movie.view.movie

import woowacourse.movie.view.item.Movie

interface MovieContract {
    interface View {
        fun showMovies(movies: List<Movie>)

        fun showTheaterInfo(movie: Movie)
    }

    interface Presenter {
        fun fetchMovies()

        fun reservationSelected(movie: Movie)
    }
}
