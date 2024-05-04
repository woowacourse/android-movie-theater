package woowacourse.movie.feature.home

interface ReservationHomeContract {
    interface View {
        fun navigateToDetail(movieId: Int)
    }

    interface Presenter {
        fun loadMovie(movieId: Int)
    }
}
