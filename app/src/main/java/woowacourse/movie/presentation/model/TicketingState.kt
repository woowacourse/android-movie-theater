package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.movieitem.Movie

@Parcelize
data class TicketingState(
    val movie: Movie,
    val ticket: Ticket = Ticket(),
    val movieDate: MovieDate? = null,
    val movieTime: MovieTime? = null,
) : Parcelable {
    val ticketCount: Int
        get() = ticket.count
    val isNotSelectedDateTime: Boolean
        get() = movieDate == null || movieTime == null
}
