package woowacourse.movie.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.data.dbhelper.ReservationDbHelper
import woowacourse.movie.data.model.ReservationEntity
import woowacourse.movie.data.model.ReservationEntity.Companion.FEE_COLUMN
import woowacourse.movie.data.model.ReservationEntity.Companion.ID_COLUMN
import woowacourse.movie.data.model.ReservationEntity.Companion.MOVIE_ID_COLUMN
import woowacourse.movie.data.model.ReservationEntity.Companion.SCREENING_DATETIME_COLUMN
import woowacourse.movie.data.model.ReservationEntity.Companion.TABLE_NAME
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.SeatRepository

class ReservationDatabase(
    val context: Context
) : ReservationRepository {

    private val db: SQLiteDatabase by lazy {
        ReservationDbHelper(context).writableDatabase
    }
    private val seatRepository: SeatRepository by lazy {
        SeatDatabase(context)
    }
    private val movieRepository: MovieRepository = MovieMockRepository

    override fun add(reservation: Reservation): Int {
        val values = ContentValues()
        with(values) {
            put(MOVIE_ID_COLUMN, reservation.movie.id)
            put(SCREENING_DATETIME_COLUMN, reservation.screeningDateTime.toString())
            put(FEE_COLUMN, reservation.finalReservationFee.amount)
        }
        return db.insert(TABLE_NAME, null, values).toInt()
    }

    override fun findAll(): List<Reservation> {

        val reservations = mutableListOf<Reservation>()

        val cursor = getReservationCursor()

        while (cursor.moveToNext()) {
            val data = ReservationEntity(
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(ID_COLUMN)
                ),
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(MOVIE_ID_COLUMN)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(SCREENING_DATETIME_COLUMN)
                ),
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(FEE_COLUMN)
                )
            )
            val movie = movieRepository.findById(data.movieId)
            val seats = seatRepository.findSeatsByReservationId(data.id)
            movie?.let {
                reservations.add(data.toDomain(movie, seats))
            }
        }
        cursor.close()

        return reservations
    }

    private fun getReservationCursor(): Cursor {
        return db.query(
            TABLE_NAME,
            arrayOf(
                ID_COLUMN,
                MOVIE_ID_COLUMN,
                SCREENING_DATETIME_COLUMN,
                FEE_COLUMN
            ),
            null, null, null, null, null
        )
    }
}
