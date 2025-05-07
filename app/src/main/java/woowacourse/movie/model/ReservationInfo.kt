package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.seat.Seats
import java.time.LocalDate

@Parcelize
data class ReservationInfo(
    val title: String,
    val date: LocalDate,
    val time: String,
    val seats: Seats,
    val price: Int,
    val theaterName: String,
) : Parcelable
