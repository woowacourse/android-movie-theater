package woowacourse.movie.presentation.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.database.Reservation
import woowacourse.movie.domain.model.reservation.MovieTicket
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity.Companion.SEAT_COL_START_VALUE
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity.Companion.SEAT_POSITION_TEXT_FORMAT
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity.Companion.SEAT_ROW_START_VALUE
import java.time.format.DateTimeFormatter

@Parcelize
data class MovieTicketUiModel(
    val ticketId: Long,
    val title: String,
    val screeningDate: String,
    val startTime: String,
    val endTime: String,
    val runningTime: Int,
    val reservationCount: Int,
    val totalPrice: Int,
    val selectedSeats: List<String>,
    val theaterName: String,
) : Parcelable {
    constructor(movieTicket: MovieTicket) : this(
        movieTicket.ticketId,
        movieTicket.reservationMovieInfo.title,
        movieTicket.reservationMovieInfo.dateTime.screeningDate.date.format(DEFAULT_DATE_FORMAT),
        movieTicket.reservationMovieInfo.dateTime.screeningDate.screeningTime.startTime.format(
            DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT),
        ),
        movieTicket.reservationMovieInfo.dateTime.screeningDate.screeningTime.getEndTime().format(
            DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT),
        ),
        movieTicket.reservationMovieInfo.dateTime.screeningInfo.runningTime,
        movieTicket.reservationInfo.reservationCount,
        movieTicket.reservationInfo.selectedSeats.totalPrice(),
        movieTicket.reservationInfo.selectedSeats.seats.map { seat ->
            String.format(
                SEAT_POSITION_TEXT_FORMAT,
                SEAT_ROW_START_VALUE + seat.row,
                SEAT_COL_START_VALUE + seat.col,
            )
        },
        movieTicket.reservationMovieInfo.theaterName,
    )

    fun screeningTime(): String = "$startTime ~ $endTime"

    fun runningTime(): String = "(${runningTime}분)"

    fun reservationInfo(): String {
        return "일반 ${reservationCount}명 | ${joinReservedSeat()} | $theaterName 극장"
    }

    private fun joinReservedSeat(): String = selectedSeats.joinToString(", ")

    fun totalPrice(): String = "${String.format("%,d", totalPrice)}원 (현장 결제)"

    companion object {
        val DEFAULT_DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        const val DEFAULT_TIME_FORMAT = "HH:mm"

        fun fromMovieTicket(movieTicket: MovieTicket): MovieTicketUiModel {
            return MovieTicketUiModel(
                movieTicket.ticketId,
                movieTicket.reservationMovieInfo.title,
                movieTicket.reservationMovieInfo.dateTime.screeningDate.date.format(
                    DEFAULT_DATE_FORMAT,
                ),
                movieTicket.reservationMovieInfo.dateTime.screeningDate.screeningTime.startTime.format(
                    DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT),
                ),
                movieTicket.reservationMovieInfo.dateTime.screeningDate.screeningTime.getEndTime()
                    .format(
                        DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT),
                    ),
                movieTicket.reservationMovieInfo.dateTime.screeningInfo.runningTime,
                movieTicket.reservationInfo.reservationCount,
                movieTicket.reservationInfo.selectedSeats.totalPrice(),
                movieTicket.reservationInfo.selectedSeats.seats.map { seat ->
                    String.format(
                        SEAT_POSITION_TEXT_FORMAT,
                        SEAT_ROW_START_VALUE + seat.row,
                        SEAT_COL_START_VALUE + seat.col,
                    )
                },
                movieTicket.reservationMovieInfo.theaterName,
            )
        }

        fun fromReservation(reservation: Reservation): MovieTicketUiModel {
            return MovieTicketUiModel(
                ticketId = reservation.ticketId,
                title = reservation.title,
                screeningDate = reservation.screeningDate,
                startTime = reservation.startTime,
                endTime = reservation.endTime,
                runningTime = reservation.runningTime,
                reservationCount = reservation.count,
                totalPrice = reservation.totalPrice,
                selectedSeats = reservation.seats,
                theaterName = reservation.theater,
            )
        }
    }
}
