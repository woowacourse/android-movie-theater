package woowacourse.movie.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.ui.model.MovieTicketModel

object TicketContract {
    const val TABLE_NAME = "ticket"
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_DATE_TIME = "date_time"
    const val COLUMN_PEOPLE_COUNT = "people_count"
    const val COLUMN_PRICE = "price"
    const val COLUMN_THEATER_NAME = "theater_name"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
        "(" +
        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
        "$COLUMN_TITLE VARCHAR(30)," +
        "$COLUMN_DATE_TIME DATETIME," +
        "$COLUMN_PEOPLE_COUNT INT," +
        "$COLUMN_PRICE INT," +
        "$COLUMN_THEATER_NAME VARCHAR(20)" +
        ");"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun createRecord(db: SQLiteDatabase, ticket: MovieTicketModel): Int {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, ticket.title)
            put(COLUMN_DATE_TIME, ticket.time.dateTime.toString())
            put(COLUMN_PEOPLE_COUNT, ticket.peopleCount.count)
            put(COLUMN_PRICE, ticket.price.amount)
            put(COLUMN_THEATER_NAME, ticket.theaterName)
        }

        return db.insert(TABLE_NAME, null, values).toInt()
    }

    fun readRecords(db: SQLiteDatabase): Cursor {
        return db.query(
            TABLE_NAME,
            null,
            null,
            null,
            COLUMN_ID,
            null,
            null
        )
    }
}
