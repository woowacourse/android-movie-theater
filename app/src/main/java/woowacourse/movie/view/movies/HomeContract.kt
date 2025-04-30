package woowacourse.movie.view.movies

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Showings

interface HomeContract {
    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun showMoviesScreen(
            movies: List<Movie>,
            navigate: (Movie) -> Unit,
        )

        fun navigateToReservation(
            movie: Movie,
            showings: Showings,
        )

        fun showTheaterSelectDialog(
            movie: Movie,
            navigate: (Showings) -> Unit,
        )
    }
}
