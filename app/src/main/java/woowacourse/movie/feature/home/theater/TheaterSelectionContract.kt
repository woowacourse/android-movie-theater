package woowacourse.movie.feature.home.theater

import woowacourse.movie.model.Theater
import woowacourse.movie.util.BasePresenter

interface TheaterSelectionContract {
    interface View {
        fun setUpTheaterAdapter(theaters: List<Theater>)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadTheaters(movieId: Long)
    }
}
