package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationInfo(
    val title: String,
    val reservationDateTime: LocalDateTime,
    val reservationCount: ReservationCount,
    val cinema: Cinema,
) : Parcelable {
    fun toTicket(seats: List<Seat>): Ticket {
        return Ticket(
            title,
            reservationDateTime,
            seats,
            reservationCount,
            cinema,
        )
    }
}
