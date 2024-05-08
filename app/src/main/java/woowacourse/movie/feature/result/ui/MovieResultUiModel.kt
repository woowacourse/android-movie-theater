package woowacourse.movie.feature.result.ui

import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.ticket.entity.Ticket
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
        fun from(ticket: Ticket): MovieResultUiModel {
            return MovieResultUiModel(
                MovieRepository.getMovieById(ticket.movieId).title,
                ticket.screeningDate.formatScreeningDate(),
                ticket.screeningTime.formatScreeningTime(),
                ticket.selectedSeats.reservationCount,
                ticket.selectedSeats.formatSeats(),
                ticket.theaterName,
                ticket.selectedSeats.totalPrice(),
            )
        }
    }
}
