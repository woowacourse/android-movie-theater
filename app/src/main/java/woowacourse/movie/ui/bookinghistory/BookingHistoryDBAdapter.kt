package woowacourse.movie.ui.bookinghistory

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.PaymentType
import woowacourse.movie.model.Mapper.toUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.TicketUiModel
import woowacourse.movie.ui.seat.SeatRow
import woowacourse.movie.util.formatScreenDateTime
import woowacourse.movie.util.toSeat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BookingHistoryDBAdapter(db: BookingHistoryDBHelper) : BookingHistoryRepository {

    private val writableDB: SQLiteDatabase = db.writableDatabase
    private val cursor = writableDB.query(
        BookingDBContract.TABLE_NAME,
        arrayOf(
            BookingDBContract.MOVIE_ID,
            BookingDBContract.MOVIE_TITLE,
            BookingDBContract.TICKET_COUNT,
            BookingDBContract.SEAT,
            BookingDBContract.BOOKING_DATE_TIME,
            BookingDBContract.PAYMENT
        ),
        null,
        null,
        null,
        null,
        null
    )

    override fun insertBookingHistory(reservationUiModel: ReservationUiModel) {
        val values = ContentValues().apply {
            put(BookingDBContract.MOVIE_ID, reservationUiModel.movieId)
            put(BookingDBContract.MOVIE_TITLE, reservationUiModel.movieTitle)
            put(BookingDBContract.TICKET_COUNT, reservationUiModel.count)
            put(BookingDBContract.PAYMENT, reservationUiModel.payment)
            put(
                BookingDBContract.SEAT,
                reservationUiModel.tickets.joinToString(",") {
                    SeatRow.find(it.seat.position.row).name + it.seat.position.column.toString()
                }
            )
            put(
                BookingDBContract.BOOKING_DATE_TIME,
                reservationUiModel.bookedDateTime.formatScreenDateTime()
            )
        }

        writableDB.insert(BookingDBContract.TABLE_NAME, null, values)
    }

    override fun loadBookingHistory(): List<ReservationUiModel> {
        val reservations = mutableListOf<ReservationUiModel>()

        while (cursor.moveToNext()) {
            val reservation = cursor.getReservation()

            reservations.add(reservation)
        }

        return reservations
    }

    fun deleteReservations() {
        if (writableDB.isOpen) {
            writableDB.delete(BookingDBContract.TABLE_NAME, null, null)
        }
    }

    private fun Cursor.getReservation(): ReservationUiModel {
        val movieId =
            getInt(getColumnIndexOrThrow(BookingDBContract.MOVIE_ID)).toLong()
        val ticketCount =
            getInt(getColumnIndexOrThrow(BookingDBContract.TICKET_COUNT))
        val seat =
            getString(getColumnIndexOrThrow(BookingDBContract.SEAT))
                .split(",")
                .map { it.toSeat().toUiModel() }
        val bookingDateTime = LocalDateTime.parse(
            getString(getColumnIndexOrThrow(BookingDBContract.BOOKING_DATE_TIME)),
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        )
        val movieTitle =
            getString(getColumnIndexOrThrow(BookingDBContract.MOVIE_TITLE))
        val payment =
            getInt(getColumnIndexOrThrow(BookingDBContract.PAYMENT))

        return ReservationUiModel(
            tickets = seat.map {
                TicketUiModel(
                    movieId = movieId,
                    bookedDateTime = bookingDateTime,
                    seat = it
                )
            }.toSet(),
            paymentType = PaymentType.OFFLINE,
            payment = payment,
            movieId = movieId,
            movieTitle = movieTitle,
            bookedDateTime = bookingDateTime,
            count = ticketCount
        )
    }

    companion object {
        private const val DATE_TIME_FORMAT = "yyyy.MM.dd HH:mm"
    }
}
