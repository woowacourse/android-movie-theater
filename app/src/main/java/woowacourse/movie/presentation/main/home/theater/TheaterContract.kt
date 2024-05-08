package woowacourse.movie.presentation.main.home.theater

interface TheaterContract {
    interface View {
        fun navigateToDetailActivity(movieId: Long, theaterId: Long)
    }

    interface Presenter {
        fun itemClicked(movieId: Long, theaterId: Long)
    }
}
