package woowacourse.movie.data.bookedticket

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TicketsDBAdapter(db: TicketDBHelper) : BookedTicketsData {

    private val writableDB: SQLiteDatabase = db.writableDatabase
    private val cursor = writableDB.query(
        TicketListDBContract.TABLE_NAME,
        arrayOf(
            TicketListDBContract.CINEMA_NAME,
            TicketListDBContract.MOVIE_ID,
            TicketListDBContract.TICKET_COUNT,
            TicketListDBContract.SEATS,
            TicketListDBContract.BOOKED_DATE_TIME,
            TicketListDBContract.PAYMENT,
        ),
        null,
        null,
        null,
        null,
        null,
    )

    override fun addTickets(ticketModel: TicketModel) {
        val values = ContentValues().apply {
            put(TicketListDBContract.MOVIE_ID, ticketModel.movieId)
            put(TicketListDBContract.CINEMA_NAME, ticketModel.cinemaName)
            put(TicketListDBContract.TICKET_COUNT, ticketModel.count)
            put(TicketListDBContract.PAYMENT, ticketModel.paymentMoney)
            put(
                TicketListDBContract.SEATS,
                ticketModel.seats.joinToString(","),
            )
            put(
                TicketListDBContract.BOOKED_DATE_TIME,
                ticketModel.bookedDateTime.format(
                    DateTimeFormatter.ofPattern(DATE_TIME_FORMAT),
                ),
            )
        }

        writableDB.insert(TicketListDBContract.TABLE_NAME, null, values)
    }

    override fun getTickets(): List<TicketModel> {
        val reservations = mutableListOf<TicketModel>()

        while (cursor.moveToNext()) {
            val reservation = cursor.getReservation()

            reservations.add(reservation)
        }

        return reservations
    }

    fun deleteReservations() {
        if (writableDB.isOpen) {
            writableDB.delete(TicketListDBContract.TABLE_NAME, null, null)
        }
    }

    private fun Cursor.getReservation(): TicketModel {
        val movieId =
            getInt(getColumnIndexOrThrow(TicketListDBContract.MOVIE_ID)).toLong()

        val cinemaName =
            getString(getColumnIndexOrThrow(TicketListDBContract.CINEMA_NAME))
        val ticketCount =
            getInt(getColumnIndexOrThrow(TicketListDBContract.TICKET_COUNT))
        val seats =
            getString(getColumnIndexOrThrow(TicketListDBContract.SEATS))
                .split(",")
        val bookingDateTime = LocalDateTime.parse(
            getString(getColumnIndexOrThrow(TicketListDBContract.BOOKED_DATE_TIME)),
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT),
        )
        val payment =
            getInt(getColumnIndexOrThrow(TicketListDBContract.PAYMENT))

        return TicketModel(
            movieId = movieId,
            cinemaName = cinemaName,
            seats = seats,
            bookedDateTime = bookingDateTime,
            count = ticketCount,
            paymentMoney = payment,
        )
    }

    companion object {
        private const val DATE_TIME_FORMAT = "yyyy.MM.dd HH:mm"
    }
}
