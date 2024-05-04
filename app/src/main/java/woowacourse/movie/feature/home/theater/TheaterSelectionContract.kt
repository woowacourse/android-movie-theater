package woowacourse.movie.feature.home.theater

import woowacourse.movie.model.Theater

interface TheaterSelectionContract {
    interface View {
        fun setUpTheaterAdapter(theaters: List<Theater>)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)
    }
}
