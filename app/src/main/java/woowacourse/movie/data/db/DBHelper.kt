package woowacourse.movie.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.data.entity.Seats

class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TicketContract.CREATE_TABLE)
        db?.execSQL(SeatContract.CREATE_TABLE)
        insertSeats(db)
        db?.execSQL(TicketSeatContract.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(TicketSeatContract.DROP_TABLE)
        db?.execSQL(TicketContract.DROP_TABLE)
        db?.execSQL(SeatContract.DROP_TABLE)
        onCreate(db)
    }

    private fun insertSeats(db: SQLiteDatabase?) {
        if (db != null) {
            Seats().getAll().forEach { SeatContract.createRecord(db, it) }
        }
    }

    companion object {
        const val DATABASE_NAME = "movie.db"
        const val DATABASE_VERSION = 1
    }
}
