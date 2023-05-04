package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.movieitem.ListItem
import java.time.LocalDateTime

@Parcelize
data class Reservation(
    val movieTitle: String,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticket: Ticket,
    val seats: PickedSeats,
    val ticketPrice: TicketPrice,
) : ListItem, Parcelable {
    val formattedDate: String
        get() = "${movieDate.year}. ${movieDate.month}. ${movieDate.day}"
    val formattedTime: String
        get() = "%02d:%02d".format(movieTime.hour, movieTime.min)
    val reservedTime: LocalDateTime
        get() = LocalDateTime.of(movieDate.year, movieDate.month, movieDate.day, movieTime.hour, movieTime.min)

    override fun isAd(): Boolean = false

    companion object {
        fun provideDummy(): List<ListItem> = List(5) {
            Reservation(
                "해리 포터와 마법사의 돌 $it",
                MovieDate(2021, 10, 10),
                MovieTime(10, 10),
                Ticket(1),
                PickedSeats(listOf(Seat(SeatRow('A'), SeatColumn(1)))),
                TicketPrice(10000)
            )
        }
    }
}
