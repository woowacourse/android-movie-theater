package woowacourse.movie.presentation.main.home.theater

import woowacourse.movie.domain.model.home.Theater

interface TheaterContract {
    interface View {
        fun showTheaters(movieId: Long, theaters: List<Theater>)

        fun navigateToDetailActivity(movieId: Long, theaterId: Long)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)

        fun itemClicked(movieId: Long, theaterId: Long)
    }
}
