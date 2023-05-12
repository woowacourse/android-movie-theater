package woowacourse.movie.db.seat

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.model.seat.SeatModel

class SeatData(
    private val db: SQLiteDatabase
) : SeatRepository {
    override fun insertSeat(seat: SeatModel): Int {
        val value = ContentValues()
        value.put(SeatContract.TABLE_COLUMN_ROW, seat.row.value)
        value.put(SeatContract.TABLE_COLUMN_COL, seat.column.value)
        value.put(SeatContract.TABLE_COLUMN_RANK, seat.rank.name)

        return db.insert(SeatContract.TABLE_NAME, null, value).toInt()
    }

    override fun getSeatById(seatId: Int): Cursor =
        db.query(
            SeatContract.TABLE_NAME,
            null,
            "${SeatContract.TABLE_COLUMN_SEAT_ID} = ?",
            arrayOf(seatId.toString()),
            null,
            null,
            null
        )

    override fun getIdByPosition(seat: SeatModel): Cursor =
        db.query(
            SeatContract.TABLE_NAME,
            arrayOf(SeatContract.TABLE_COLUMN_SEAT_ID),
            "${SeatContract.TABLE_COLUMN_ROW} = ? AND ${SeatContract.TABLE_COLUMN_COL} = ?",
            arrayOf(seat.row.value.toString(), seat.column.value.toString()),
            null,
            null,
            null
        )
}
