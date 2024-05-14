package woowacourse.movie.feature.history.ui

import woowacourse.movie.data.movie.dto.Movie
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.util.formatScreeningDate
import woowacourse.movie.util.formatScreeningTime

class ReservationHistoryUiModel(
    val movieTitle: String,
    val screeningDate: String,
    val screeningTime: String,
    val theaterName: String,
) {
    companion object {
        fun from(
            movie: Movie,
            ticket: Ticket,
        ): ReservationHistoryUiModel {
            return ReservationHistoryUiModel(
                movie.title,
                ticket.screeningDate.formatScreeningDate(),
                ticket.screeningTime.formatScreeningTime(),
                ticket.theaterName,
            )
        }
    }
}
