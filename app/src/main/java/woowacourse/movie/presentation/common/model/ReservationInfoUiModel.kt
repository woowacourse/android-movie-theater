package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationInfo
import java.time.LocalDateTime

@Parcelize
class ReservationInfoUiModel(
    val title: String,
    val reservationDateTime: LocalDateTime,
    val reservationCount: Int,
    val seats: List<SeatUiModel>,
    val theaterName: String,
) : Parcelable

fun ReservationInfo.toUiModel(theaterName: String): ReservationInfoUiModel =
    ReservationInfoUiModel(
        title,
        reservationDateTime,
        reservationCount.value,
        seats.map { it.toUiModel() },
        theaterName,
    )

fun ReservationInfoUiModel.toDomain(): ReservationInfo {
    val info =
        ReservationInfo(
            title,
            reservationDateTime,
            ReservationCount(reservationCount),
        )

    this.seats.forEach { info.updateSeats(it.toDomain()) }

    return info
}
