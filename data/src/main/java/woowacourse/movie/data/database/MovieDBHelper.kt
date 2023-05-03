package woowacourse.movie.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.data.database.table.SQLMovie
import woowacourse.movie.data.database.table.SQLReservation
import woowacourse.movie.data.database.table.SQLSeat
import woowacourse.movie.data.database.table.SQLTable
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
    private val tables: List<SQLTable> = listOf(SQLMovie, SQLReservation, SQLSeat)

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

    private fun insert(db: SQLiteDatabase?, table: SQLTable, row: Map<String, Any>): Long {
        db ?: return -1

        val contentValues = ContentValues()

        for (item in row) {
            val column = table.scheme.find { it.name == item.key } ?: continue
            contentValues.put(column, item.value)
        }

        return db.insert(table.name, null, contentValues)
    }

    private fun ContentValues.put(column: SQLColumn, value: Any) {
        when (column.type) {
            is SQLType.INTEGER -> put(column.name, value as Int)
            is SQLType.TEXT -> put(column.name, value as String)
        }
    }

    fun selectAllReservations(): List<Reservation> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM ${SQLReservation.name}", null)
        val reservations = mutableListOf<Reservation>()
        with(cursor) {
            while (moveToNext()) {
                reservations.add(
                    Reservation(
                        selectMovieById(getInt(getColumnIndexOrThrow(SQLReservation.MOVIE_ID))),
                        ReservationDetail(
                            LocalDateTime.parse(getString(getColumnIndexOrThrow(SQLReservation.DATE))),
                            getInt(getColumnIndexOrThrow(SQLReservation.PEOPLE_COUNT))
                        ),
                        selectSeatsByReservationId(getInt(getColumnIndexOrThrow(SQLReservation.ID))),
                        Price(getInt(getColumnIndexOrThrow(SQLReservation.PRICE)))
                    )
                )
            }
        }
        cursor.close()
        return reservations
    }

    private fun selectSeatsByReservationId(reservationId: Int): Seats {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SQLSeat.name} WHERE ${SQLSeat.RESERVATION_ID} = $reservationId", null
        )
        val seats = mutableListOf<Seat>()

        with(cursor) {
            while (moveToNext()) {
                seats.add(
                    Seat(
                        MovieSeatRow(getInt(getColumnIndexOrThrow(SQLSeat.ROW))),
                        getInt(getColumnIndexOrThrow(SQLSeat.COLUMN))
                    )
                )
            }
        }
        cursor.close()
        return Seats(seats)
    }

    private fun selectMovieById(id: Int): Movie {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SQLMovie.name} WHERE ${SQLMovie.ID} = $id", null
        )
        val movie = with(cursor) {
            moveToNext()
            Movie(
                Image(getInt(getColumnIndexOrThrow(SQLMovie.POSTER))),
                getString(getColumnIndexOrThrow(SQLMovie.TITLE)),
                DateRange(
                    LocalDate.parse(getString(getColumnIndexOrThrow(SQLMovie.START_DATE))),
                    LocalDate.parse(getString(getColumnIndexOrThrow(SQLMovie.END_DATE)))
                ),
                getInt(getColumnIndexOrThrow(SQLMovie.RUNNING_TIME)),
                getString(getColumnIndexOrThrow(SQLMovie.DESCRIPTION))
            )
        }
        cursor.close()
        return movie
    }

    private fun selectMovieIdByTitle(title: String): Long {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SQLMovie.name} WHERE ${SQLMovie.TITLE} = '$title'", null
        )
        val id = with(cursor) {
            moveToNext()
            getInt(getColumnIndexOrThrow(SQLMovie.ID))
        }
        cursor.close()
        return id.toLong()
    }

    fun selectAllMovies(): List<Movie> {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SQLMovie.name}", null
        )
        val movies: MutableList<Movie> = mutableListOf()
        with(cursor) {
            while (moveToNext()) {
                movies.add(
                    Movie(
                        Image(getInt(getColumnIndexOrThrow(SQLMovie.POSTER))),
                        getString(getColumnIndexOrThrow(SQLMovie.TITLE)),
                        DateRange(
                            LocalDate.parse(getString(getColumnIndexOrThrow(SQLMovie.START_DATE))),
                            LocalDate.parse(getString(getColumnIndexOrThrow(SQLMovie.END_DATE)))
                        ),
                        getInt(getColumnIndexOrThrow(SQLMovie.RUNNING_TIME)),
                        getString(getColumnIndexOrThrow(SQLMovie.DESCRIPTION))
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
        contentValues[SQLReservation.DATE] = reservation.reservationDetail.date.toString()
        contentValues[SQLReservation.PEOPLE_COUNT] = reservation.reservationDetail.peopleCount
        contentValues[SQLReservation.MOVIE_ID] = movieId.toInt()
        contentValues[SQLReservation.PRICE] = reservation.price.value
        val reservationId = insert(db, SQLReservation, contentValues)
        for (seat in reservation.seats.value) {
            insertSeat(db, seat, reservationId)
        }
    }

    fun insertMovie(db: SQLiteDatabase?, movie: Movie): Long {
        val contentValues: MutableMap<String, Any> = mutableMapOf()
        contentValues[SQLMovie.POSTER] = movie.poster.resource
        contentValues[SQLMovie.TITLE] = movie.title
        contentValues[SQLMovie.START_DATE] = movie.date.startDate.toString()
        contentValues[SQLMovie.END_DATE] = movie.date.endDate.toString()
        contentValues[SQLMovie.RUNNING_TIME] = movie.runningTime
        contentValues[SQLMovie.DESCRIPTION] = movie.description
        return insert(db, SQLMovie, contentValues)
    }

    private fun insertSeat(db: SQLiteDatabase?, seat: Seat, reservationId: Long) {
        val contentValues: MutableMap<String, Any> = mutableMapOf()
        contentValues[SQLSeat.RESERVATION_ID] = reservationId.toInt()
        contentValues[SQLSeat.ROW] = seat.row.row
        contentValues[SQLSeat.COLUMN] = seat.column
        insert(db, SQLSeat, contentValues)
    }

    companion object {
        const val DB_NAME = "movie"
        const val DB_VERSION = 1
    }
}
