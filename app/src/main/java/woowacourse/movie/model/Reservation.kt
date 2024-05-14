package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.db.ReservationEntity
import woowacourse.movie.utils.formatSeat
import java.text.DecimalFormat

@Parcelize
data class Reservation(
    val movieTitle: String,
    val screeningDate: String,
    val screeningTime: String,
    val selectedSeats: List<Seat>,
    val theaterName: String,
) : Parcelable {
    private val totalPrice = selectedSeats.sumOf { it.seatGrade.price }
    val totalCount = selectedSeats.size

    fun selectedSeatsToString(): String = selectedSeats.joinToString(", ") { formatSeat(it) }

    fun totalPriceToString(): String = decimal.format(totalPrice)

    companion object {
        val decimal = DecimalFormat("#,###")
    }
}

fun Reservation.toReservationEntity(): ReservationEntity {
    return ReservationEntity(
        movieTitle = movieTitle,
        screeningDate = screeningDate,
        screeningTime = screeningTime,
        selectedSeats = selectedSeats,
        theaterName = theaterName,
    )
}
