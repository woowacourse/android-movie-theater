package woowacourse.movie.feature.result

import woowacourse.movie.model.MovieTicket

interface MovieResultContract {
    interface View {
        fun displayMovieTicket(movieTicketData: MovieTicket?)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter {
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
