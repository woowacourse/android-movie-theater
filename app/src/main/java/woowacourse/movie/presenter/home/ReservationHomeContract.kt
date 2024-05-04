package woowacourse.movie.presenter.home

import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie

interface ReservationHomeContract {
    interface View {
        fun navigateToDetail(movieId: Int)

        fun showMovieData(
            movies: List<Movie>,
            ads: List<Advertisement>,
        )
    }

    interface Presenter {
        fun loadMovies()

        fun loadMovie(movie: Movie)
    }
}
