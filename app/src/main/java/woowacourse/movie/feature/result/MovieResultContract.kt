package woowacourse.movie.feature.result

import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.InvalidMovieIdErrorListener

interface MovieResultContract {
    interface View : InvalidMovieIdErrorListener {
        fun displayMovieTicket(movieTicketData: MovieTicket?)
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
