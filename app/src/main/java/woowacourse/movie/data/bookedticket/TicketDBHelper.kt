package woowacourse.movie.data.bookedticket

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TicketDBHelper(
    context: Context,
) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    1,
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${TicketListDBContract.TABLE_NAME}(" +
                "${TicketListDBContract.MOVIE_ID} int," +
                "${TicketListDBContract.CINEMA_NAME} text," +
                "${TicketListDBContract.SEATS} text," +
                "${TicketListDBContract.BOOKED_DATE_TIME} text," +
                "${TicketListDBContract.PAYMENT} int," +
                "${TicketListDBContract.TICKET_COUNT} int" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS booking_history")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "verver.db"
    }
}
