package woowacourse.movie.repository.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import woowacourse.movie.domain.model.reservation.MovieTicket
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.domain.model.reservation.ReservationMovieInfo
import woowacourse.movie.domain.model.reservation.seat.SelectedSeats
import woowacourse.movie.presentation.uimodel.TicketUiModel
import java.time.LocalDate

@Entity(tableName = "reservationTicket")
data class ReservationTicketEntity(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Long = 0,
    val movieTitle: String,
    val theaterName: String,
    val screenDate: String,
    val screenTime: String,
    val seats: SelectedSeats,
)

fun ReservationTicketEntity.toMovieTicket(
    reservationMovieInfo: ReservationMovieInfo,
    reservationInfo: ReservationInfo
): MovieTicket {
    return MovieTicket(
        ticketId = this.ticketId,
        reservationMovieInfo = reservationMovieInfo,
        reservationInfo = reservationInfo
    )
}

fun ReservationTicketEntity.toTicketUiModel(): TicketUiModel {
    return TicketUiModel(
        ticketId = this.ticketId,
        title = this.movieTitle,
        startTime = this.screenTime,
        screeningDate = this.screenDate,
        theaterName = this.theaterName
    )
}
