package woowacourse.movie.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.data.database.table.SqlMovie
import woowacourse.movie.data.database.table.SqlReservation
import woowacourse.movie.data.database.table.SqlSeat
import woowacourse.movie.data.database.table.SqlTable
import woowacourse.movie.domain.DateRange
import woowacourse.movie.domain.Image
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Price
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.domain.seat.MovieSeatRow
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.domain.seat.Seats
import java.time.LocalDate
import java.time.LocalDateTime

class MovieDBHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val tables: List<SqlTable> = listOf(SqlMovie, SqlReservation, SqlSeat)

    override fun onCreate(db: SQLiteDatabase?) {
        tables.forEach {
            val columns =
                it.scheme.joinToString(",") { scheme -> "${scheme.name} ${scheme.type} ${scheme.constraint}" }
            db?.execSQL("CREATE TABLE ${it.name} ($columns)")
        }

        initMockData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        tables.forEach {
            db?.execSQL("DROP TABLE IF EXISTS ${it.name}")
        }
        onCreate(db)
    }

    private fun initMockData(db: SQLiteDatabase?) {
        for (movie in MovieMock.createMovies()) {
            insertMovie(db, movie)
        }
    }

    private fun insert(db: SQLiteDatabase?, table: SqlTable, row: Map<String, Any>): Long {
        db ?: return -1

        val contentValues = ContentValues()

        for (item in row) {
            val column = table.scheme.find { it.name == item.key } ?: continue
            contentValues.put(column, item.value)
        }

        return db.insert(table.name, null, contentValues)
    }

    private fun ContentValues.put(column: SqlColumn, value: Any) {
        when (column.type) {
            is SqlType.INTEGER -> put(column.name, value as Int)
            is SqlType.TEXT -> put(column.name, value as String)
        }
    }

    fun selectAllReservations(): List<Reservation> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM ${SqlReservation.name}", null)
        val reservations = mutableListOf<Reservation>()
        with(cursor) {
            while (moveToNext()) {
                reservations.add(
                    Reservation(
                        selectMovieById(getInt(getColumnIndexOrThrow(SqlReservation.MOVIE_ID))),
                        ReservationDetail(
                            LocalDateTime.parse(getString(getColumnIndexOrThrow(SqlReservation.DATE))),
                            getInt(getColumnIndexOrThrow(SqlReservation.PEOPLE_COUNT))
                        ),
                        selectSeatsByReservationId(getInt(getColumnIndexOrThrow(SqlReservation.ID))),
                        Price(getInt(getColumnIndexOrThrow(SqlReservation.PRICE))),
                        getString(getColumnIndexOrThrow(SqlReservation.THEATER_NAME))
                    )
                )
            }
        }
        cursor.close()
        return reservations
    }

    private fun selectSeatsByReservationId(reservationId: Int): Seats {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SqlSeat.name} WHERE ${SqlSeat.RESERVATION_ID} = $reservationId", null
        )
        val seats = mutableListOf<Seat>()

        with(cursor) {
            while (moveToNext()) {
                seats.add(
                    Seat(
                        MovieSeatRow(getInt(getColumnIndexOrThrow(SqlSeat.ROW))),
                        getInt(getColumnIndexOrThrow(SqlSeat.COLUMN))
                    )
                )
            }
        }
        cursor.close()
        return Seats(seats)
    }

    private fun selectMovieById(id: Int): Movie {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SqlMovie.name} WHERE ${SqlMovie.ID} = $id", null
        )
        val movie = with(cursor) {
            moveToNext()
            Movie(
                Image(getInt(getColumnIndexOrThrow(SqlMovie.POSTER))),
                getString(getColumnIndexOrThrow(SqlMovie.TITLE)),
                DateRange(
                    LocalDate.parse(getString(getColumnIndexOrThrow(SqlMovie.START_DATE))),
                    LocalDate.parse(getString(getColumnIndexOrThrow(SqlMovie.END_DATE)))
                ),
                getInt(getColumnIndexOrThrow(SqlMovie.RUNNING_TIME)),
                getString(getColumnIndexOrThrow(SqlMovie.DESCRIPTION))
            )
        }
        cursor.close()
        return movie
    }

    private fun selectMovieIdByTitle(title: String): Long {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SqlMovie.name} WHERE ${SqlMovie.TITLE} = '$title'", null
        )
        val id = with(cursor) {
            moveToNext()
            getInt(getColumnIndexOrThrow(SqlMovie.ID))
        }
        cursor.close()
        return id.toLong()
    }

    fun selectAllMovies(): List<Movie> {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SqlMovie.name}", null
        )
        val movies: MutableList<Movie> = mutableListOf()
        with(cursor) {
            while (moveToNext()) {
                movies.add(
                    Movie(
                        Image(getInt(getColumnIndexOrThrow(SqlMovie.POSTER))),
                        getString(getColumnIndexOrThrow(SqlMovie.TITLE)),
                        DateRange(
                            LocalDate.parse(getString(getColumnIndexOrThrow(SqlMovie.START_DATE))),
                            LocalDate.parse(getString(getColumnIndexOrThrow(SqlMovie.END_DATE)))
                        ),
                        getInt(getColumnIndexOrThrow(SqlMovie.RUNNING_TIME)),
                        getString(getColumnIndexOrThrow(SqlMovie.DESCRIPTION))
                    )
                )
            }
        }
        cursor.close()
        return movies
    }

    fun insertReservation(db: SQLiteDatabase?, reservation: Reservation) {
        val movieId = selectMovieIdByTitle(reservation.movie.title)
        val contentValues: MutableMap<String, Any> = mutableMapOf()
        contentValues[SqlReservation.DATE] = reservation.reservationDetail.date.toString()
        contentValues[SqlReservation.PEOPLE_COUNT] = reservation.reservationDetail.peopleCount
        contentValues[SqlReservation.MOVIE_ID] = movieId.toInt()
        contentValues[SqlReservation.PRICE] = reservation.price.value
        contentValues[SqlReservation.THEATER_NAME] = reservation.theaterName
        val reservationId = insert(db, SqlReservation, contentValues)
        for (seat in reservation.seats.value) {
            insertSeat(db, seat, reservationId)
        }
    }

    fun insertMovie(db: SQLiteDatabase?, movie: Movie): Long {
        val contentValues: MutableMap<String, Any> = mutableMapOf()
        contentValues[SqlMovie.POSTER] = movie.poster.resource
        contentValues[SqlMovie.TITLE] = movie.title
        contentValues[SqlMovie.START_DATE] = movie.date.startDate.toString()
        contentValues[SqlMovie.END_DATE] = movie.date.endDate.toString()
        contentValues[SqlMovie.RUNNING_TIME] = movie.runningTime
        contentValues[SqlMovie.DESCRIPTION] = movie.description
        return insert(db, SqlMovie, contentValues)
    }

    private fun insertSeat(db: SQLiteDatabase?, seat: Seat, reservationId: Long) {
        val contentValues: MutableMap<String, Any> = mutableMapOf()
        contentValues[SqlSeat.RESERVATION_ID] = reservationId.toInt()
        contentValues[SqlSeat.ROW] = seat.row.row
        contentValues[SqlSeat.COLUMN] = seat.column
        insert(db, SqlSeat, contentValues)
    }

    companion object {
        const val DB_NAME = "movie"
        const val DB_VERSION = 1
    }
}
