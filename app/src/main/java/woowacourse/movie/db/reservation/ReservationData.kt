package woowacourse.movie.db.reservation

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ReservationData(
    private val db: SQLiteDatabase
) : ReservationRepository {
    override fun insertReservationIds(ticketId: Int, seatId: Int) {
        val values = ContentValues()
        values.put(ReservationContract.TABLE_COLUMN_TICKET_ID, ticketId)
        values.put(ReservationContract.TABLE_COLUMN_SEAT_ID, seatId)

        db.insert(ReservationContract.TABLE_NAME, null, values)
    }

    override fun getReservationIds(ticketId: Int): Cursor =
        db.query(
            ReservationContract.TABLE_NAME,
            null,
            "${ReservationContract.TABLE_COLUMN_TICKET_ID} = ?",
            arrayOf(ticketId.toString()),
            null,
            null,
            null
        )
}
