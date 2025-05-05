package woowacourse.movie.view.home

import woowacourse.movie.domain.Showings
import woowacourse.movie.view.home.movies.MovieUi

interface HomeContract {
    interface Presenter {
        fun fetchData()
    }

    interface View {
        fun showMoviesScreen(
            movieUis: List<MovieUi>,
            navigate: (MovieUi) -> Unit,
        )

        fun navigateToReservation(
            movieUi: MovieUi,
            showings: Showings,
        )

        fun showTheaterSelectDialog(
            movieUi: MovieUi,
            navigate: (Showings) -> Unit,
        )
    }
}
