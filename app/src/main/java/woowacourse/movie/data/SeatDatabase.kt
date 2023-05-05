package woowacourse.movie.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.data.dbhelper.SeatDbHelper
import woowacourse.movie.data.model.SeatEntity
import woowacourse.movie.data.model.SeatEntity.Companion.COLUMN_COLUMN
import woowacourse.movie.data.model.SeatEntity.Companion.RESERVATION_ID_COLUMN
import woowacourse.movie.data.model.SeatEntity.Companion.ROW_COLUMN
import woowacourse.movie.data.model.SeatEntity.Companion.TABLE_NAME
import woowacourse.movie.domain.Seat

class SeatDatabase(
    val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        SeatDbHelper(context).writableDatabase
    }

    fun addSeats(seats: List<Seat>, reservationId: Int) {
        seats.forEach { seat ->
            val values = ContentValues()
            with(values) {
                put(RESERVATION_ID_COLUMN, reservationId)
                put(ROW_COLUMN, seat.row)
                put(COLUMN_COLUMN, seat.column)
            }
            db.insert(TABLE_NAME, null, values)
        }
    }

    fun findSeatsByReservationId(id: Int): List<Seat> {
        val seats = mutableListOf<Seat>()

        val cursor = getSeatsCursor(id)
        while (cursor.moveToNext()) {
            val data = SeatEntity(
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(RESERVATION_ID_COLUMN)
                ),
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(ROW_COLUMN)
                ),
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_COLUMN)
                )
            )
            seats.add(data.toDomain())
        }

        return seats
    }

    private fun getSeatsCursor(id: Int): Cursor {
        return db.query(
            TABLE_NAME,
            arrayOf(
                RESERVATION_ID_COLUMN,
                ROW_COLUMN,
                COLUMN_COLUMN
            ),
            "$RESERVATION_ID_COLUMN = ?",
            arrayOf(id.toString()), null, null, null
        )
    }
}
