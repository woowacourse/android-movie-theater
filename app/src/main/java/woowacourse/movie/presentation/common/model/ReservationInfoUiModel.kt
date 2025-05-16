package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationInfo
import java.time.LocalDateTime

@Parcelize
class ReservationInfoUiModel(
    val title: String,
    val theaterName: String,
    val reservationDateTime: LocalDateTime,
    val reservationCount: Int,
    val seats: List<SeatUiModel>,
) : Parcelable

fun ReservationInfo.toUiModel(): ReservationInfoUiModel =
    ReservationInfoUiModel(
        title,
        theaterName,
        reservationDateTime,
        reservationCount.value,
        seats.map { it.toUiModel() },
    )

fun ReservationInfoUiModel.toDomain(): ReservationInfo {
    val info =
        ReservationInfo(
            title,
            theaterName,
            reservationDateTime,
            ReservationCount(reservationCount),
        )

    this.seats.forEach { info.addSeat(it.toDomain()) }

    return info
}
