package woowacourse.movie.data.reservation

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import woowacourse.movie.data.reservation.ReservationContract.ReservationEntry
import woowacourse.movie.data.reservation.ReservationContract.ReservationSeatsEntry
import woowacourse.movie.domain.screening.Minute
import woowacourse.movie.domain.screening.Movie
import woowacourse.movie.domain.screening.Reservation
import woowacourse.movie.domain.theater.Point
import woowacourse.movie.repository.ReservationRepository
import woowacourse.movie.repository.TheaterRepository
import java.lang.IllegalStateException
import java.time.LocalDateTime

class ReservationRepositoryImpl(private val db: SQLiteDatabase) : ReservationRepository {
    override fun save(reservation: Reservation): Long {
        saveReservation(reservation)
        saveSeatPoints(reservation)
        return reservation.id ?: throw IllegalStateException("예매 객체의 아이디가 널이면 save 메서드의 로직이 잘못된 것임")
    }

    private fun saveReservation(reservation: Reservation) {
        ContentValues().apply {
            put(BaseColumns._ID, reservation.id)
            put(ReservationEntry.COLUMN_NAME_MOVIE_TITLE, reservation.movie.title)
            put(
                ReservationEntry.COLUMN_NAME_MOVIE_RUNNING_TIME,
                reservation.movie.runningTime.value
            )
            put(ReservationEntry.COLUMN_NAME_MOVIE_SUMMARY, reservation.movie.summary)
            put(
                ReservationEntry.COLUMN_NAME_SCREENING_DATE_TIME,
                reservation.screeningDateTime.toString()
            )
            put(ReservationEntry.COLUMN_NAME_THEATER_ID, reservation.theater.id)
        }.run {
            val reservationId = db.insert(ReservationEntry.TABLE_NAME, null, this)
            reservation.id = reservationId
        }
    }

    private fun saveSeatPoints(reservation: Reservation) {
        reservation.seatPoints.forEach {
            ContentValues().apply {
                put(ReservationSeatsEntry.COLUMN_NAME_RESERVATION_ID, reservation.id)
                put(ReservationSeatsEntry.COLUMN_NAME_ROW, it.row)
                put(ReservationSeatsEntry.COLUMN_NAME_COLUMN, it.column)
            }.run { db.insert(ReservationSeatsEntry.TABLE_NAME, null, this) }
        }
    }

    override fun findById(id: Long): Reservation {
        val seatPoints = querySeatPoints(id)

        return queryReservation(id, seatPoints)
    }

    private fun querySeatPoints(reservationId: Long): List<Point> {
        val seatPoints = mutableListOf<Point>()
        val projection =
            arrayOf(ReservationSeatsEntry.COLUMN_NAME_ROW, ReservationSeatsEntry.COLUMN_NAME_COLUMN)
        val selection = "${ReservationSeatsEntry.COLUMN_NAME_RESERVATION_ID} = $reservationId"
        val cursor = db.query(
            ReservationSeatsEntry.TABLE_NAME,
            projection,
            selection,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val row =
                cursor.getInt(cursor.getColumnIndexOrThrow(ReservationSeatsEntry.COLUMN_NAME_ROW))
            val column =
                cursor.getInt(cursor.getColumnIndexOrThrow(ReservationSeatsEntry.COLUMN_NAME_COLUMN))
            seatPoints.add(Point(row, column))
        }
        cursor.close()
        return seatPoints
    }

    private fun queryReservation(reservationId: Long, seatPoints: List<Point>): Reservation {
        val cursor = db.rawQuery(
            "SELECT * FROM ${ReservationEntry.TABLE_NAME} " +
                    "JOIN ${ReservationSeatsEntry.TABLE_NAME} " +
                    "ON ${BaseColumns._ID} = ${ReservationSeatsEntry.COLUMN_NAME_RESERVATION_ID} " +
                    "WHERE ${BaseColumns._ID} = $reservationId", null
        )

        val reservation = cursor.let {
            it.moveToNext()
            val id = it.getLong(it.getColumnIndexOrThrow(BaseColumns._ID))
            val movieTitle =
                it.getString(it.getColumnIndexOrThrow(ReservationEntry.COLUMN_NAME_MOVIE_TITLE))
            val movieRunningTime =
                it.getInt(it.getColumnIndexOrThrow(ReservationEntry.COLUMN_NAME_MOVIE_RUNNING_TIME))
            val movieSummary =
                it.getString(it.getColumnIndexOrThrow(ReservationEntry.COLUMN_NAME_MOVIE_SUMMARY))
            val screeningDateTime =
                LocalDateTime.parse(it.getString(it.getColumnIndexOrThrow(ReservationEntry.COLUMN_NAME_SCREENING_DATE_TIME)))
            val theater =
                TheaterRepository.findById(it.getLong(it.getColumnIndexOrThrow(ReservationEntry.COLUMN_NAME_THEATER_ID)))
                    ?: throw IllegalStateException("극장 외래키 무결성 깨짐")
            Reservation(
                Movie(movieTitle, Minute(movieRunningTime), movieSummary),
                screeningDateTime,
                theater,
                seatPoints
            ).apply { this.id = id }
        }
        cursor.close()

        return reservation
    }

    override fun findAll(): List<Reservation> {
        val reservations = mutableListOf<Reservation>()
        val cursor = db.query(
            ReservationEntry.TABLE_NAME,
            arrayOf(BaseColumns._ID),
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val reservationId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            reservations.add(findById(reservationId))
        }
        cursor.close()
        return reservations
    }
}