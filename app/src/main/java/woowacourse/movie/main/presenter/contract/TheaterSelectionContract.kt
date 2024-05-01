package woowacourse.movie.main.presenter.contract

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater

interface TheaterSelectionContract {
    interface View {
        fun setUpTheaterAdapter(theaters: List<Theater>)
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)
    }
}