package woowacourse.movie.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.ui.model.seat.SeatModel

object SeatContract {
    const val TABLE_NAME = "seat"
    const val COLUMN_ID = "id"
    const val COLUMN_ROW = "row"
    const val COLUMN_COLUMN = "column"
    const val COLUMN_RANK = "rank"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
        "(" +
        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
        "$COLUMN_ROW INT," +
        "$COLUMN_COLUMN INT," +
        "$COLUMN_RANK CHARACTER" +
        ");"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun createRecord(db: SQLiteDatabase, seat: SeatModel): Int {
        val values = ContentValues()
        values.put(COLUMN_ROW, seat.row.value)
        values.put(COLUMN_COLUMN, seat.column.value)
        values.put(COLUMN_RANK, seat.rank.name)

        return db.insert(TABLE_NAME, null, values).toInt()
    }

    fun readRecordById(db: SQLiteDatabase, id: Int): Cursor {
        return db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
    }

    fun readRecordBySeat(db: SQLiteDatabase, seat: SeatModel): Cursor {
        return db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            "$COLUMN_ROW = ? AND $COLUMN_COLUMN = ?",
            arrayOf(seat.row.value.toString(), seat.column.value.toString()),
            null,
            null,
            null
        )
    }
}
