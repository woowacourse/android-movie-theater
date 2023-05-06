package woowacourse.movie.data.reservation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.util.DATETIME_FORMATTER
import woowacourse.movie.view.mapper.toDomain
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.SeatUiModel
import java.time.LocalDateTime

class ReservationDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ReservationConstract.TABLE_NAME} (" +
                "  ${ReservationConstract.TABLE_COLUMN_TITLE} varchar(30)," +
                "  ${ReservationConstract.TABLE_COLUMN_SCREENING_TIME} int," +
                "  ${ReservationConstract.TABLE_COLUMN_SEATS} text," +
                "  ${ReservationConstract.TABLE_COLUMN_PRICE} int," +
                "  ${ReservationConstract.TABLE_COLUMN_THEATER} varchar(100)" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ReservationConstract.TABLE_NAME}")
        onCreate(db)
    }

    fun insert(reservation: Reservation) {
        val values = ContentValues()
        values.put(ReservationConstract.TABLE_COLUMN_TITLE, reservation.title)
        values.put(ReservationConstract.TABLE_COLUMN_SCREENING_TIME, reservation.screeningDateTime.format(DATETIME_FORMATTER))
        values.put(ReservationConstract.TABLE_COLUMN_SEATS, reservation.seats.joinToString { it.toUiModel().seatId })
        values.put(ReservationConstract.TABLE_COLUMN_PRICE, reservation.price.price)
        values.put(ReservationConstract.TABLE_COLUMN_THEATER, reservation.theaterName)
        writableDatabase.insert(ReservationConstract.TABLE_NAME, null, values)
    }

    fun selectReservations(): List<Reservation> {
        val reservations = mutableListOf<Reservation>()
        val sql = "select * from ${ReservationConstract.TABLE_NAME}"
        val cursor: Cursor = readableDatabase.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val title: String = cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstract.TABLE_COLUMN_TITLE))
            val screeningDateTimes: String = cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstract.TABLE_COLUMN_SCREENING_TIME))
            val seats: String = cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstract.TABLE_COLUMN_SEATS))
            val price: Int = cursor.getInt(cursor.getColumnIndexOrThrow(ReservationConstract.TABLE_COLUMN_PRICE))
            val theater: String = cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstract.TABLE_COLUMN_THEATER))
            reservations.add(Reservation(title, LocalDateTime.parse(screeningDateTimes, DATETIME_FORMATTER), seats.split(",").map { SeatUiModel.of(it).toDomain() }, Price(price), theater))
        }
        cursor.close()
        return reservations
    }

    companion object {
        private const val DATABASE_NAME = "system"
        private const val DATABASE_VERSION = 1
    }
}
