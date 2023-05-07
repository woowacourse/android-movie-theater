package woowacourse.movie.data.reservation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.uimodel.MovieTicketModel
import woowacourse.movie.uimodel.PeopleCountModel
import woowacourse.movie.uimodel.SeatModel
import woowacourse.movie.uimodel.SelectedSeatsModel
import java.time.LocalDateTime

class ReservationDBHelper(
    context: Context,
) : SQLiteOpenHelper(context, "movie.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ReservationConstant.TABLE_NAME} (" +
                "${ReservationConstant.TABLE_COLUMN_TITLE} varchar(30)," +
                "${ReservationConstant.TABLE_COLUMN_TIME} varchar(20)," +
                "${ReservationConstant.TABLE_COLUMN_COUNT} int," +
                "${ReservationConstant.TABLE_COLUMN_SEATS} text" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ReservationConstant.TABLE_NAME};")
        onCreate(db)
    }

    fun insert(ticket: MovieTicketModel) {
        val value = ContentValues().apply {
            put(ReservationConstant.TABLE_COLUMN_TITLE, ticket.title)
            put(ReservationConstant.TABLE_COLUMN_TIME, ticket.time.toString())
            put(ReservationConstant.TABLE_COLUMN_COUNT, ticket.peopleCount.count)
            put(ReservationConstant.TABLE_COLUMN_SEATS, ticket.seats.toString())
        }

        writableDatabase.insert(ReservationConstant.TABLE_NAME, null, value)
    }

    fun getAll(): List<MovieTicketModel> {
        val tickets = mutableListOf<MovieTicketModel>()

        val cursor: Cursor = readableDatabase.rawQuery(
            "Select * from ${ReservationConstant.TABLE_NAME}",
            null,
        )
        while (cursor.moveToNext()) {
            val title: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstant.TABLE_COLUMN_TITLE))
            val time: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstant.TABLE_COLUMN_TIME))
            val count: Int =
                cursor.getInt(cursor.getColumnIndexOrThrow(ReservationConstant.TABLE_COLUMN_COUNT))
            val seats: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ReservationConstant.TABLE_COLUMN_SEATS))

            tickets.add(
                MovieTicketModel(
                    title,
                    LocalDateTime.parse(time),
                    PeopleCountModel(count),
                    SelectedSeatsModel(seats.split(",").map { SeatModel.of(it.trim()) }.toSet()),
                ),
            )
        }

        cursor.close()
        return tickets
    }
}
