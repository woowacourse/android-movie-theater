package woowacourse.movie.presentation.model

import android.os.Parcelable
import com.example.domain.Reservation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReservationResult(
    val id: Long,
    val totalPrice: Int,
    val ticketCount: Int,
    val seatNames: String,
    val movieTitle: String,
    val date: String,
    val time: String,
) : Parcelable {

    companion object {
        fun from(reservation: Reservation): ReservationResult {
            return ReservationResult(
                reservation.id ?: -1,
                reservation.totalPrice,
                reservation.ticketCount,
                reservation.seatNames,
                reservation.movieTitle,
                reservation.date,
                reservation.time
            )
        }
    }
}
