package woowacourse.movie.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TicketDataDBHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "ticketInfo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${TicketDataContract.TABLE_NAME} (" +
                "  ${TicketDataContract.TABLE_COLUMN_TITLE} varchar(30) not null," +
                "  ${TicketDataContract.TABLE_COLUMN_DATE} varchar(30) not null," +
                "  ${TicketDataContract.TABLE_COLUMN_TIME} varchar(30) not null," +
                "  ${TicketDataContract.TABLE_COLUMN_COUNT} int," +
                "  ${TicketDataContract.TABLE_COLUMN_SEATS} varchar(30) not null," +
                "  ${TicketDataContract.TABLE_COLUMN_THEATER} varchar(30) not null," +
                "  ${TicketDataContract.TABLE_COLUMN_PRICE} int" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TicketDataContract.TABLE_NAME}")
        onCreate(db)
    }
}
