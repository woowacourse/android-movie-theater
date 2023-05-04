package woowacourse.movie.ui.bookinghistory

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookingHistoryDBHelper(
    context: Context
) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${BookingDBContract.TABLE_NAME}(" +
                "${BookingDBContract.MOVIE_ID} int," +
                "${BookingDBContract.SEAT} text," +
                "${BookingDBContract.MOVIE_TITLE} text," +
                "${BookingDBContract.BOOKING_DATE_TIME} text," +
                "${BookingDBContract.PAYMENT} int," +
                "${BookingDBContract.TICKET_COUNT} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS booking_history")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "woogi.db"
    }
}
