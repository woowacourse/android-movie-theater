package woowacourse.movie.list.contract

interface TheaterContract {
    interface View {
        fun navigateToDetailActivity(movieId: Long, theaterId: Long)
    }

    interface Presenter {
        fun itemClicked(movieId: Long, theaterId: Long)
    }
}
