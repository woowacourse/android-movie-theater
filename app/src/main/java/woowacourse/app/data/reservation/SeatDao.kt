package woowacourse.app.data.reservation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.app.data.CgvContract.Seat.TABLE_COLUMN_COLUMN
import woowacourse.app.data.CgvContract.Seat.TABLE_COLUMN_RANK
import woowacourse.app.data.CgvContract.Seat.TABLE_COLUMN_RESERVATION_ID
import woowacourse.app.data.CgvContract.Seat.TABLE_COLUMN_ROW
import woowacourse.app.data.CgvContract.Seat.TABLE_NAME
import woowacourse.app.data.CgvDbHelper

class SeatDao(context: Context) : SeatDataSource {
    private val cgvDb by lazy { CgvDbHelper(context).readableDatabase }

    override fun insertSeat(reservationId: Long, rank: Int, row: Int, column: Int): SeatEntity {
        val data = ContentValues()
        data.put(TABLE_COLUMN_RESERVATION_ID, reservationId)
        data.put(TABLE_COLUMN_RANK, rank)
        data.put(TABLE_COLUMN_ROW, row)
        data.put(TABLE_COLUMN_COLUMN, column)
        val seatId = cgvDb.insert(TABLE_NAME, null, data)
        return getSeatEntity(seatId)!!
    }

    override fun getSeatEntities(reservationId: Long): List<SeatEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val selection = "$TABLE_COLUMN_RESERVATION_ID == ?"
        val selectionArgs = arrayOf("$reservationId")
        val cursor = makeCursor(selection, selectionArgs, orderBy)
        val seats = readSeats(cursor)
        cursor.close()
        return seats
    }

    override fun getSeatEntity(id: Long): SeatEntity? {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$id")
        val cursor = makeCursor(selection, selectionArgs, null)
        val reservation = readSeat(cursor)
        cursor.close()
        return reservation
    }

    private fun readSeats(cursor: Cursor): List<SeatEntity> {
        val seats = mutableListOf<SeatEntity>()
        while (true) {
            val seat: SeatEntity = readSeat(cursor) ?: break
            seats.add(seat)
        }
        return seats
    }

    private fun readSeat(cursor: Cursor): SeatEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val reservationId =
            cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_RESERVATION_ID))
        val rank = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_RANK))
        val row = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ROW))
        val column = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_COLUMN))
        return SeatEntity(id, reservationId, rank, row, column)
    }

    private fun makeCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        orderBy: String?,
    ): Cursor {
        return cgvDb.query(
            TABLE_NAME,
            arrayOf(
                BaseColumns._ID,
                TABLE_COLUMN_RESERVATION_ID,
                TABLE_COLUMN_RANK,
                TABLE_COLUMN_ROW,
                TABLE_COLUMN_COLUMN,
            ),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }
}
