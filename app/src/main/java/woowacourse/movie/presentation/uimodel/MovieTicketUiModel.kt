package woowacourse.movie.presentation.uimodel

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler
import woowacourse.movie.R
import woowacourse.movie.domain.model.reservation.MovieTicket
import woowacourse.movie.presentation.reservation.seat.SeatSelectionActivity.Companion.SEAT_COL_START_VALUE
import woowacourse.movie.presentation.reservation.seat.SeatSelectionActivity.Companion.SEAT_POSITION_TEXT_FORMAT
import woowacourse.movie.presentation.reservation.seat.SeatSelectionActivity.Companion.SEAT_ROW_START_VALUE
import java.time.format.DateTimeFormatter

@Parcelize
data class MovieTicketUiModel(
    val ticketId: Long,
    val title: String,
    val screeningDate: String,
    val startTime: String,
    val reservationCount: Int,
    val totalPrice: Int,
    val selectedSeats: List<String>,
    val theaterName: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString() ?: "",
    )

    constructor(movieTicket: MovieTicket) : this(
        movieTicket.ticketId,
        movieTicket.reservationMovieInfo.title,
        movieTicket.reservationMovieInfo.dateTime.screeningDate.date.format(DEFAULT_DATE_FORMAT),
        movieTicket.reservationMovieInfo.dateTime.screeningDate.screeningTime.startTime.format(
            DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT),
        ),
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

    fun screeningTime(context: Context): String {
        return context.getString(R.string.screening_time_format, startTime)
    }

    fun reservationInfo(context: Context): String {
        return context.getString(
            R.string.reservation_info_format,
            reservationCount,
            joinReservedSeat(),
            theaterName,
        )
    }

    private fun joinReservedSeat(): String = selectedSeats.joinToString(", ")

    fun totalPrice(context: Context): String {
        return context.getString(R.string.reservation_total_price_format, totalPrice)
    }

    companion object : Parceler<MovieTicketUiModel> {
        val DEFAULT_DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        const val DEFAULT_TIME_FORMAT = "HH:mm"

        override fun MovieTicketUiModel.write(
            parcel: Parcel,
            flags: Int,
        ) {
            parcel.writeLong(ticketId)
            parcel.writeString(title)
            parcel.writeString(screeningDate)
            parcel.writeString(startTime)
            parcel.writeInt(reservationCount)
            parcel.writeInt(totalPrice)
            parcel.writeStringList(selectedSeats)
            parcel.writeString(theaterName)
        }

        override fun create(parcel: Parcel): MovieTicketUiModel {
            return MovieTicketUiModel(parcel)
        }
    }
}
