package woowacourse.movie.database

import android.content.ContentValues
import android.content.Context
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Price
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.ReservationDetail
import woowacourse.movie.domain.seat.MovieSeatRow
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.domain.seat.Seats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationDB(context: Context) {
    private val db = ReservationDBHelper(context).writableDatabase

    fun insertData(reservation: Reservation) {
        val reservationId = insertReservationData(reservation)
        insertMovieData(reservation.movie, reservationId)
    }

    private fun insertReservationData(reservation: Reservation): Long {
        val values = ContentValues()
        values.put(
            ReservationTableNames.TABLE_COLUMN_DATE,
            reservation.reservationDetail.date.format(
                DateTimeFormatter.ofPattern(RESERVATION_DATE_TIME_FORMAT)
            )
        )
        values.put(
            ReservationTableNames.TABLE_COLUMN_COUNT, reservation.reservationDetail.peopleCount
        )
        val formattedSeats = reservation.seats.value.joinToString {
            "${it.row.row}-${it.column}"
        }
        values.put(ReservationTableNames.TABLE_COLUMN_SEATS, formattedSeats)
        values.put(ReservationTableNames.TABLE_COLUMN_PRICE, reservation.price.value)
        values.put(
            ReservationTableNames.TABLE_COLUMN_THEATER_NAME,
            reservation.reservationDetail.theaterName
        )
        return db.insert(ReservationTableNames.TABLE_NAME, null, values)
    }

    private fun insertMovieData(movie: Movie, reservationId: Long) {
        val values = ContentValues()
        values.put(MovieTableNames.TABLE_COLUMN_POSTER, movie.poster.resource)
        values.put(
            MovieTableNames.TABLE_COLUMN_TITLE, movie.title
        )
//        val dateFormat = DateTimeFormatter.ofPattern(MOVIE_DATE_FORMAT)
        values.put(
            MovieTableNames.TABLE_COLUMN_START_DATE,
            movie.date.startDate.format(
                DateTimeFormatter.ofPattern(MOVIE_DATE_FORMAT)
            )
        )
        values.put(
            MovieTableNames.TABLE_COLUMN_END_DATE,
            movie.date.endDate.format(
                DateTimeFormatter.ofPattern(MOVIE_DATE_FORMAT)
            )
        )
        values.put(MovieTableNames.TABLE_COLUMN_RUNNING_TIME, movie.runningTime)
        values.put(MovieTableNames.TABLE_COLUMN_DESCRIPTION, movie.description)
        values.put(MovieTableNames.TABLE_COLUMN_RESERVATION_ID, reservationId)
        db.insert(MovieTableNames.TABLE_NAME, null, values)
    }

    fun getReservationData(): MutableList<Reservation>? {
        val data = mutableListOf<Reservation>()
        val movieCursor = db.query(
            MovieTableNames.TABLE_NAME, null, null, null, null, null, null
        )

        while (movieCursor.moveToNext()) {
            val poster = movieCursor.getInt(
                movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_POSTER)
            )
            val title = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_TITLE)
            )
            val startDate: LocalDate = LocalDate.parse(
                movieCursor.getString(
                    movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_START_DATE)
                ),
                DateTimeFormatter.ofPattern(MOVIE_DATE_FORMAT)
            )
            val endDate: LocalDate = LocalDate.parse(
                movieCursor.getString(
                    movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_END_DATE)
                ),
                DateTimeFormatter.ofPattern(MOVIE_DATE_FORMAT)
            )
            val runningTime = movieCursor.getInt(
                movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_RUNNING_TIME)
            )
            val description = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_DESCRIPTION)
            )
            val reservationId = movieCursor.getInt(
                movieCursor.getColumnIndexOrThrow(MovieTableNames.TABLE_COLUMN_RESERVATION_ID)
            )

            val reservationCursor = db.query(
                ReservationTableNames.TABLE_NAME,
                null,
                "${ReservationTableNames.TABLE_COLUMN_ID}=?",
                arrayOf(reservationId.toString()),
                null,
                null,
                null
            )
            val movie =
                Movie(Image(poster), title, DateRange(startDate, endDate), runningTime, description)
            var reservationDetail: ReservationDetail?
            var reservation: Reservation? = null
            if (reservationCursor.moveToNext()) {
                val date = reservationCursor.getString(
                    reservationCursor.getColumnIndexOrThrow(ReservationTableNames.TABLE_COLUMN_DATE)
                )
                val peopleCount = reservationCursor.getInt(
                    reservationCursor.getColumnIndexOrThrow(
                        ReservationTableNames.TABLE_COLUMN_COUNT
                    )
                )
                val seatsString = reservationCursor.getString(
                    reservationCursor.getColumnIndexOrThrow(ReservationTableNames.TABLE_COLUMN_SEATS)
                )
                val seats: Seats = seatsString.split(", ").map {
                    val rowColumn = it.split("-")
                    val row = rowColumn[0].toInt()
                    val column = rowColumn[1].toInt()
                    Seat(MovieSeatRow(row), column)
                }.let { Seats(it) }
                val price = reservationCursor.getInt(
                    reservationCursor.getColumnIndexOrThrow(ReservationTableNames.TABLE_COLUMN_PRICE)
                )

                val formattedDateTime = LocalDateTime.parse(
                    date,
                    DateTimeFormatter.ofPattern(
                        RESERVATION_DATE_TIME_FORMAT
                    )
                )
                val theaterName = reservationCursor.getString(
                    reservationCursor.getColumnIndexOrThrow(ReservationTableNames.TABLE_COLUMN_THEATER_NAME)
                )

                reservationDetail = ReservationDetail(formattedDateTime, peopleCount, theaterName)
                reservation =
                    Reservation(movie, reservationDetail, seats, Price(price))
            }
            reservationCursor.close()

            requireNotNull(reservation) { return null }
            data.add(reservation)
        }
        movieCursor.close()

        return data
    }

    fun deleteDB() {
        db.delete(ReservationTableNames.TABLE_NAME, null, null)
        db.delete(MovieTableNames.TABLE_NAME, null, null)
    }

    companion object {
        private const val MOVIE_DATE_FORMAT = "yyyy-MM-dd"
        private const val RESERVATION_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    }
}
