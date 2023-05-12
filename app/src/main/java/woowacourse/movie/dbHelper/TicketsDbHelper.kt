package woowacourse.movie.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_COLUMN_TICKETS_CINEMA_NAME
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_COLUMN_TICKETS_COLUMN
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_COLUMN_TICKETS_DATE_TIME
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_COLUMN_TICKETS_ID
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_COLUMN_TICKETS_MOVIE_NAME
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_COLUMN_TICKETS_ROW
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_NAME_SEAT_SELECT
import woowacourse.movie.dbHelper.TicketsConstract.TABLE_NAME_TICKETS
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState

class TicketsDbHelper(context: Context?) : SQLiteOpenHelper(context, "ark.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_TICKETS (" +
                "  $TABLE_COLUMN_TICKETS_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  $TABLE_COLUMN_TICKETS_CINEMA_NAME TEXT," +
                "  $TABLE_COLUMN_TICKETS_MOVIE_NAME TEXT," +
                "  $TABLE_COLUMN_TICKETS_DATE_TIME TEXT" +
                ");"
        )

        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_SEAT_SELECT (" +
                "  $TABLE_COLUMN_TICKETS_ID INTEGER," +
                "  $TABLE_COLUMN_TICKETS_ROW INTEGER," +
                "  $TABLE_COLUMN_TICKETS_COLUMN INTEGER" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_TICKETS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_SEAT_SELECT")
        onCreate(db)
    }

    fun insert(ticketsState: TicketsState) {
        val wDb = this.writableDatabase
        val values = ContentValues().apply {
            put(TABLE_COLUMN_TICKETS_CINEMA_NAME, ticketsState.cinemaName)
            put(TABLE_COLUMN_TICKETS_MOVIE_NAME, ticketsState.movieName)
            put(TABLE_COLUMN_TICKETS_DATE_TIME, ticketsState.dateTime.toString())
        }
        wDb.insert(TABLE_NAME_TICKETS, null, values)
        // get id
        val cursor = wDb.rawQuery("SELECT last_insert_rowid()", null)
        cursor.moveToFirst()
        val id = cursor.getInt(0)

        ticketsState.positions.forEach {
            insert(id = id, row = it.row, column = it.column)
        }
        cursor.close()
    }

    private fun insert(id: Int, row: Int, column: Int) {
        val wDb = this.writableDatabase
        val values = ContentValues().apply {
            put(TABLE_COLUMN_TICKETS_ID, id)
            put(TABLE_COLUMN_TICKETS_ROW, row)
            put(TABLE_COLUMN_TICKETS_COLUMN, column)
        }
        wDb.insert(TABLE_NAME_SEAT_SELECT, null, values)
    }

    fun getAll(): List<TicketsState> {
        val rDb = this.readableDatabase
        val cursor = rDb.rawQuery("SELECT * FROM $TABLE_NAME_TICKETS", null)
        val ticketsStates = mutableListOf<TicketsState>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TICKETS_ID))
            val cinemaName = cursor.getString(
                cursor.getColumnIndexOrThrow(TABLE_COLUMN_TICKETS_CINEMA_NAME)
            )
            val movieName = cursor.getString(
                cursor.getColumnIndexOrThrow(TABLE_COLUMN_TICKETS_MOVIE_NAME)
            )
            val dateTime = cursor.getString(
                cursor.getColumnIndexOrThrow(TABLE_COLUMN_TICKETS_DATE_TIME)
            )
            val positions = getPositions(id)

            ticketsStates.add(
                TicketsState(cinemaName, movieName, LocalDateTime.parse(dateTime), positions)
            )
        }

        cursor.close()
        return ticketsStates
    }

    private fun getPositions(id: Int): List<SeatPositionState> {
        val rDb = this.readableDatabase
        val cursor = rDb.rawQuery(
            "SELECT * FROM $TABLE_NAME_SEAT_SELECT WHERE $TABLE_COLUMN_TICKETS_ID = $id",
            null
        )
        val positions = mutableListOf<SeatPositionState>()

        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TICKETS_ROW))
            val column = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TICKETS_COLUMN))

            positions.add(SeatPositionState(row = row, column = column))
        }

        cursor.close()
        return positions
    }
}
