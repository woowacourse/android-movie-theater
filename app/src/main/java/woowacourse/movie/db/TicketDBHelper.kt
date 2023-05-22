package woowacourse.movie.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.Movie
import domain.Reservation
import domain.Seat
import domain.Theater
import domain.Ticket
import domain.Tickets
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.data.mock.MockMoviesFactory
import woowacourse.movie.data.model.mapper.ReservationMapper
import woowacourse.movie.data.model.mapper.SeatMapper
import woowacourse.movie.data.model.uimodel.SeatUIModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TicketDBHelper(context: Context?) : SQLiteOpenHelper(context, "Ticket.db", null, 1), DBHelper {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${TicketContract.TABLE_NAME} " +
            "(${TicketContract.TITLE} varchar(30), " +
            "${TicketContract.THEATER} varchar(30), " +
            "${TicketContract.DATE_TIME} varchar(30), " +
            "${TicketContract.SEATS} varchar(50));"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE if exists ${TicketContract.TABLE_NAME}"
        db?.execSQL(sql)
        onCreate(db)
    }

    override fun saveReservation(reservation: Reservation) {
        val db = writableDatabase

        val movieTitle = reservation.movie.title
        val theaterName = reservation.detail.list.first().theater.name
        val reservationDate = reservation.detail.list.first().date
        val formattedDateTime =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(reservationDate)
        val seats = ReservationMapper.toUI(reservation).tickets.list.joinToString(",") {
            it.seat.row.toString() + it.seat.col.toString()
        }.trim()
        val values = ContentValues().apply {
            put(TicketContract.TITLE, movieTitle)
            put(TicketContract.THEATER, theaterName)
            put(TicketContract.DATE_TIME, formattedDateTime)
            put(TicketContract.SEATS, seats)
        }
        db.insert(TicketContract.TABLE_NAME, null, values)
        db.close()
    }

    override fun getReservations(): List<Reservation> {
        val db = readableDatabase

        val cursor =
            db.rawQuery("SELECT * FROM ${TicketContract.TABLE_NAME}", null)
        val reservations = mutableListOf<Reservation>()
        while (cursor.moveToNext()) {
            val movie = getMovie(cursor)
            val tickets = getTickets(cursor)
            reservations.add(Reservation(movie, tickets))
        }
        cursor.close()
        db.close()
        return reservations
    }

    private fun getMovie(cursor: Cursor): Movie {
        val movieTitle =
            cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.TITLE))
        return MockMoviesFactory.movies.find(movieTitle)
    }

    private fun getTickets(cursor: Cursor): Tickets {
        val reservationDate = getReservationDate(cursor)
        val seats = getSeats(cursor)
        val theaterName = getTheaterName(cursor)
        return Tickets(
            seats.map { seat ->
                Ticket(date = reservationDate, seat, DisCountPolicies(), Theater(theaterName, listOf()))
            }
        )
    }

    private fun getTheaterName(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.THEATER))
    }

    private fun getReservationDate(cursor: Cursor): LocalDateTime {
        val formattedDateTime =
            cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.DATE_TIME))
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return LocalDateTime.parse(formattedDateTime, formatter)
    }

    private fun getSeats(cursor: Cursor): List<Seat> {
        val stringSeats =
            cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.SEATS))
        val splitStringSeats = stringSeats.trim().split(",")
        return splitStringSeats.map {
            SeatMapper.toDomain(SeatUIModel(it.first(), it.substring(1).toInt()))
        }
    }
}
