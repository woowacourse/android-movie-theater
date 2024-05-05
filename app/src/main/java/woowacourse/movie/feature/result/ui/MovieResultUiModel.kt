package woowacourse.movie.feature.result.ui

import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.formatScreeningDate
import woowacourse.movie.util.formatScreeningTime
import woowacourse.movie.util.formatSeats

class MovieResultUiModel(
    val title: String,
    val screeningDate: String,
    val screeningTime: String,
    val seatCount: Int,
    val selectedSeats: String,
    val theaterName: String,
    val totalPrice: Int,
) {
    companion object {
        fun from(movieTicket: MovieTicket): MovieResultUiModel {
            return MovieResultUiModel(
                movieTicket.movieTitle,
                movieTicket.screeningDate.formatScreeningDate(),
                movieTicket.screeningTime.formatScreeningTime(),
                movieTicket.reservationCount,
                movieTicket.formatSeats(),
                movieTicket.theaterName,
                movieTicket.movieSelectedSeats.totalPrice(),
            )
        }
    }
}
