package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import woowacourse.movie.data.model.MovieBookingEntity

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
        fun from(bookings: MovieBookingEntity): ReservationResult {
            return ReservationResult(
                bookings.id,
                bookings.totalPrice,
                bookings.personCount,
                bookings.seatNames,
                bookings.title,
                bookings.date,
                bookings.time
            )
        }
    }
}
