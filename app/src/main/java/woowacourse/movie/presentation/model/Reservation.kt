package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.movieitem.ListItem

@Parcelize
data class Reservation(
    val movieTitle: String,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticket: Ticket,
    val seats: PickedSeats,
    val ticketPrice: TicketPrice,
) : ListItem, Parcelable {
    override fun isAd(): Boolean = false
}
