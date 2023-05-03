package woowacourse.app.model.ticket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.app.model.movie.MovieUiModel
import woowacourse.app.ui.seat.SeatRow
import java.time.LocalDateTime

@Parcelize
data class TicketUiModel(
    val movie: MovieUiModel,
    val bookedDateTime: LocalDateTime,
    val seat: SeatUiModel,
) : Parcelable {
    val coordinate get() = SeatRow.find(seat.position.row).name + (seat.position.column + 1).toString()
}
