package woowacourse.movie.feature.history.ui

import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.util.formatScreeningDate
import woowacourse.movie.util.formatScreeningTime

class ReservationHistoryUiModel(
    val movieTitle: String,
    val screeningDate: String,
    val screeningTime: String,
    val theaterName: String,
) {
    companion object {
        fun from(ticket: Ticket): ReservationHistoryUiModel {
            return ReservationHistoryUiModel(
                MovieRepository.getMovieById(ticket.movieId).title,
                ticket.screeningDate.formatScreeningDate(),
                ticket.screeningTime.formatScreeningTime(),
                ticket.theaterName,
            )
        }
    }
}
