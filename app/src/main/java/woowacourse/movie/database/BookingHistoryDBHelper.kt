package woowacourse.movie.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookingHistoryDBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_QUERY)
    }

    companion object {
        const val DB_NAME = "BookingHistoryDB"
        private const val DB_VERSION: Int = 1
        const val TABLE_NAME = "booking_history"
        const val KEY_TITLE = "title"
        const val KEY_DATE = "date"
        const val KEY_TIME = "time"
        const val KEY_TICKET_COUNT = "ticket_count"
        const val KEY_SEATS = "seats"
        const val KEY_TOTAL_PRICE = "total_price"

        private const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$KEY_TITLE text," +
                "$KEY_DATE text," +
                "$KEY_TIME text," +
                "$KEY_TICKET_COUNT INTEGER NOT NULL," +
                "$KEY_SEATS text," +
                "$KEY_TOTAL_PRICE text" +
                ");"
        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
