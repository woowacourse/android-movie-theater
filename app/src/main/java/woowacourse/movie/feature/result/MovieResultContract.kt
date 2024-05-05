package woowacourse.movie.feature.result

import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.BasePresenter

interface MovieResultContract {
    interface View {
        fun displayMovieTicket(movieTicket: MovieTicket)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadMovieTicket(
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            movieCount: Int,
            selectedSeats: String,
            theaterName: String,
        )
    }
}
