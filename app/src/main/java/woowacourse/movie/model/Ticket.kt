package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(val movieTitle: String, val screeningDateTime: String, val selectedSeats: List<Seat>, val theaterId: Long = 0L) :
    Parcelable {
    val totalPrice = selectedSeats.sumOf { it.seatGrade.price }
    val totalCount = selectedSeats.size
}
