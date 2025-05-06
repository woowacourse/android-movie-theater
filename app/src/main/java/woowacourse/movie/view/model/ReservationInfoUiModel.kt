package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TicketCount
import java.time.LocalDateTime

@Parcelize
data class ReservationInfoUiModel(
    val title: String,
    val dateTime: LocalDateTime,
    val seats: SeatsUiModel,
    val count: Int,
    val theaterName: String,
) : Parcelable

fun ReservationInfo.toPresentation(theaterName: String): ReservationInfoUiModel =
    ReservationInfoUiModel(
        title,
        dateTime,
        seats.toPresentation(),
        count.value,
        theaterName,
    )

fun ReservationInfoUiModel.toDomain(): ReservationInfo =
    ReservationInfo(
        title,
        dateTime,
        Seats.create(),
        TicketCount(count),
    )
