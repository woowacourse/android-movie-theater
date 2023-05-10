package woowacourse.movie.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.*
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.mock.MockMoviesFactory
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.model.mapper.ReservationMapper.toUi
import woowacourse.movie.model.mapper.SeatMapper.toDomain
import woowacourse.movie.util.DateTimeFormatHelper.toLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationDbHelper(context: Context) :
    SQLiteOpenHelper(context, "${ReservationContract.TABLE_NAME}", null, 1),
    ReservationDbHelperInterface {
    private val reservationWritableDb: SQLiteDatabase = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ReservationContract.TABLE_NAME} (" +
                    "${ReservationContract.TABLE_COLUMN_MOVIE_TITLE} varchar(30) not null," +
                    "${ReservationContract.TABLE_COLUMN_RESERVATION_DATE} varchar(20) not null," +
                    "${ReservationContract.TABLE_COLUMN_THEATER_NAME} varchar(30) not null," +
                    "${ReservationContract.TABLE_COLUMN_SEATS} varchar(100) not null" +
                    ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ReservationContract.TABLE_NAME}")
        onCreate(db)
    }

    override fun saveReservation(reservation: Reservation) {
        val movieTitle = reservation.movie.title
        val theaterName = reservation.tickets.list.first().theaterName
        val reservationDate = reservation.tickets.list.first().date
        val formattedDateTime =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(reservationDate)
        val seats = reservation.toUi().tickets.list.joinToString(",") {
            it.seat.row.toString() + it.seat.col.toString()
        }.trim()
        val values = ContentValues().apply {
            put(ReservationContract.TABLE_COLUMN_MOVIE_TITLE, movieTitle)
            put(ReservationContract.TABLE_COLUMN_THEATER_NAME, theaterName)
            put(ReservationContract.TABLE_COLUMN_RESERVATION_DATE, formattedDateTime)
            put(ReservationContract.TABLE_COLUMN_SEATS, seats)
        }
        reservationWritableDb.insert(ReservationContract.TABLE_NAME, null, values)
    }

    override fun getReservations(): List<Reservation> {
        val cursor =
            reservationWritableDb.rawQuery("SELECT * FROM ${ReservationContract.TABLE_NAME}", null)
        val reservations = mutableListOf<Reservation>()
        while (cursor.moveToNext()) {
            val movie = getMovie(cursor)
            val tickets = getTickets(cursor)
            reservations.add(Reservation(movie, tickets))
        }
        return reservations
    }

    private fun getMovie(cursor: Cursor): Movie {
        val movieTitle =
            cursor.getString(cursor.getColumnIndexOrThrow(ReservationContract.TABLE_COLUMN_MOVIE_TITLE))
        return MockMoviesFactory.movies.findMovieByTitle(movieTitle) ?: throw IllegalStateException(
            "잘못된 영화가 저장되었습니다!!"
        )
    }

    private fun getTickets(cursor: Cursor): Tickets {
        val reservationDate = getReservationDate(cursor)
        val seats = getSeats(cursor)
        val theaterName = getTheaterName(cursor)
        return Tickets(seats.map {
            Ticket(date = reservationDate, it, theaterName, DisCountPolicies())
        })
    }

    private fun getTheaterName(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(ReservationContract.TABLE_COLUMN_THEATER_NAME))
    }

    private fun getReservationDate(cursor: Cursor): LocalDateTime {
        val formattedDateTime =
            cursor.getString(cursor.getColumnIndexOrThrow(ReservationContract.TABLE_COLUMN_RESERVATION_DATE))
        return formattedDateTime.toLocalDateTime()
    }

    private fun getSeats(cursor: Cursor): List<Seat> {
        val stringSeats =
            cursor.getString(cursor.getColumnIndexOrThrow(ReservationContract.TABLE_COLUMN_SEATS))
        val splitStringSeats = stringSeats.trim().split(",")
        return splitStringSeats.map {
            SeatUiModel(it.first(), it.substring(1).toInt())
                .toDomain()
        }
    }
}
