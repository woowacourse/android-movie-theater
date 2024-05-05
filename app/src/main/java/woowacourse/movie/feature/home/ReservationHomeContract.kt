package woowacourse.movie.feature.home

import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie

interface ReservationHomeContract {
    interface View {
        fun showMovieCatalog(
            movies: List<Movie>,
            advertisements: List<Advertisement>,
        )

        fun navigateToTheaterSelection(movieId: Int)
    }

    interface Presenter {
        fun loadMovieCatalog()

        fun sendMovieIdToTheaterSelection(movieId: Int)
    }
}
