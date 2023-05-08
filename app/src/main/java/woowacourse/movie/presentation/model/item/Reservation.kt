package woowacourse.movie.presentation.model.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import java.time.LocalDateTime

@Parcelize
data class Reservation(
    val movieTitle: String,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticket: Ticket,
    val seats: PickedSeats,
    val theaterName: String,
    val ticketPrice: TicketPrice,
) : ListItem, Parcelable {
    val formattedDate: String
        get() = "${movieDate.year}. ${movieDate.month}. ${movieDate.day}"
    val formattedTime: String
        get() = "%02d:%02d".format(movieTime.hour, movieTime.min)
    val reservedTime: LocalDateTime
        get() = LocalDateTime.of(movieDate.year, movieDate.month, movieDate.day, movieTime.hour, movieTime.min)
}
