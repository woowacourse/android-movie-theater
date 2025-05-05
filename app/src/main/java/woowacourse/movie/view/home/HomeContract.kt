package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieItem
import woowacourse.movie.domain.Showings

interface HomeContract {
    interface Presenter {
        fun loadMovies()

        fun onMovieSelected(movie: Movie)

        fun onTheaterSelected(
            movie: Movie,
            showings: Showings,
        )
    }

    interface View {
        fun showMovies(movieItems: List<MovieItem>)

        fun showTheaterSelectDialog(movie: Movie)

        fun navigateToReservation(
            movie: Movie,
            showings: Showings,
        )
    }
}
