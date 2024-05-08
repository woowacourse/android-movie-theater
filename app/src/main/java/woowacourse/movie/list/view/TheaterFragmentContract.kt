package woowacourse.movie.list.view

interface TheaterFragmentContract {
    interface View {
        fun navigateToDetailActivity(movieId: Long, theaterId: Long)
    }

    interface Presenter {
        fun itemClicked(movieId: Long, theaterId: Long)
    }
}
