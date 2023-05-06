package woowacourse.movie.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

object TicketSeatContract {
    const val TABLE_NAME = "ticket_seat"
    const val COLUMN_TICKET_ID = "ticket_id"
    const val COLUMN_SEAT_ID = "seat_id"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
        "(" +
        "$COLUMN_TICKET_ID INT," +
        "$COLUMN_SEAT_ID INT," +
        "PRIMARY KEY ($COLUMN_TICKET_ID, $COLUMN_SEAT_ID)," +
        "FOREIGN KEY ($COLUMN_TICKET_ID)" +
        "REFERENCES ${TicketContract.TABLE_NAME} (${TicketContract.COLUMN_ID})," +
        "FOREIGN KEY ($COLUMN_SEAT_ID)" +
        "REFERENCES ${SeatContract.TABLE_NAME} (${SeatContract.COLUMN_ID})" +
        ");"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun createRecord(db: SQLiteDatabase, ticketId: Int, seatId: Int) {
        val values = ContentValues()
        values.put(COLUMN_TICKET_ID, ticketId)
        values.put(COLUMN_SEAT_ID, seatId)

        db.insert(TABLE_NAME, null, values)
    }

    fun readRecords(db: SQLiteDatabase, ticketId: Int): Cursor {
        return db.query(
            TABLE_NAME,
            null,
            "$COLUMN_TICKET_ID = ?",
            arrayOf(ticketId.toString()),
            null,
            null,
            null
        )
    }
}
