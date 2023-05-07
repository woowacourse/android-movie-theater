package woowacourse.movie.presentation.activities.main.fragments.history

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.item.Reservation

class HistoryDbHelper(
    val context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE ${HistoryDbContract.TABLE_NAME} (" +
            "  ${HistoryDbContract.TABLE_MOVIE_TITLE} TEXT," +
            "  ${HistoryDbContract.TABLE_MOVIE_DATE} TEXT," +
            "  ${HistoryDbContract.TABLE_MOVIE_TIME} TEXT," +
            "  ${HistoryDbContract.TABLE_TICKET_COUNT} int," +
            "  ${HistoryDbContract.TABLE_SEAT} TEXT," +
            "  ${HistoryDbContract.TABLE_PRICE} int" +
            ");"

        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${HistoryDbContract.TABLE_NAME}")
        onCreate(db)
    }

    fun insertData(reservation: Reservation) {
        val db = this.writableDatabase
        val data = ContentValues().apply {
            put(HistoryDbContract.TABLE_MOVIE_TITLE, reservation.movieTitle)
            put(HistoryDbContract.TABLE_MOVIE_DATE, reservation.formattedDate)
            put(HistoryDbContract.TABLE_MOVIE_TIME, reservation.formattedTime)
            put(HistoryDbContract.TABLE_TICKET_COUNT, reservation.ticket.count)
            put(HistoryDbContract.TABLE_SEAT, reservation.seats.toString())
            put(HistoryDbContract.TABLE_PRICE, reservation.ticketPrice.amount)
        }
        db.insert(HistoryDbContract.TABLE_NAME, null, data)
        db.close()
    }

    fun getData(): List<Reservation> {
        val db = this.writableDatabase
        val cursor = db.query(
            HistoryDbContract.TABLE_NAME,
            arrayOf(
                HistoryDbContract.TABLE_MOVIE_TITLE,
                HistoryDbContract.TABLE_MOVIE_DATE,
                HistoryDbContract.TABLE_MOVIE_TIME,
                HistoryDbContract.TABLE_TICKET_COUNT,
                HistoryDbContract.TABLE_SEAT,
                HistoryDbContract.TABLE_PRICE,
            ),
            null,
            null,
            null,
            null,
            null,
        )
        val reservations = mutableListOf<Reservation>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDbContract.TABLE_MOVIE_TITLE))
            val movieDate = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDbContract.TABLE_MOVIE_DATE))
            val movieTime = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDbContract.TABLE_MOVIE_TIME))
            val ticketCount = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryDbContract.TABLE_TICKET_COUNT))
            val seats = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDbContract.TABLE_SEAT))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryDbContract.TABLE_PRICE))

            reservations.add(
                Reservation(
                    movieTitle = title,
                    movieDate = MovieDate.from(movieDate),
                    movieTime = MovieTime.from(movieTime),
                    ticket = Ticket(ticketCount),
                    seats = PickedSeats.from(seats),
                    ticketPrice = TicketPrice(price),
                ),
            )
        }
        cursor.close()
        db.close()
        return reservations
    }

    companion object {
        private const val DB_NAME = "krrong_db"
    }
}
