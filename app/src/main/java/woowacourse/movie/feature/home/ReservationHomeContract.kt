package woowacourse.movie.feature.home

import woowacourse.movie.model.movie.Movie

interface ReservationHomeContract {
    interface View {
        fun navigateToDetail(movieId: Int)
    }

    interface Presenter {
        fun loadMovie(movie: Movie)
    }
}
