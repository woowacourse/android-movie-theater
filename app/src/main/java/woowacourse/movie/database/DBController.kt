package woowacourse.movie.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.dto.BookingHistoryDto
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.dto.movie.MovieDateDto
import woowacourse.movie.dto.movie.MovieTimeDto
import woowacourse.movie.dto.ticket.TicketCountDto
import java.time.LocalDate
import java.time.LocalTime

class DBController(private val writableDB: SQLiteDatabase) {
    fun insertDB(bookingMovie: BookingMovieModel) {
        val values = ContentValues()
        values.put(TicketDataContract.TABLE_COLUMN_TITLE, bookingMovie.title)
        values.put(TicketDataContract.TABLE_COLUMN_DATE, bookingMovie.date.date.toString())
        values.put(TicketDataContract.TABLE_COLUMN_TIME, bookingMovie.time.time.toString())
        values.put(TicketDataContract.TABLE_COLUMN_COUNT, bookingMovie.ticketCount.numberOfPeople)
        values.put(TicketDataContract.TABLE_COLUMN_SEATS, bookingMovie.seats)
        values.put(TicketDataContract.TABLE_COLUMN_THEATER, bookingMovie.theaterName)
        values.put(TicketDataContract.TABLE_COLUMN_PRICE, bookingMovie.price)
        writableDB.insert(TicketDataContract.TABLE_NAME, null, values)
    }

    fun findAllDB() {
        val cursor = writableDB.rawQuery("SELECT * FROM ${TicketDataContract.TABLE_NAME}", null)
        while (cursor.moveToNext()) {
            val title =
                cursor.getString(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_TITLE))
            val date =
                cursor.getString(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_DATE))
            val time =
                cursor.getString(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_TIME))
            val count =
                cursor.getInt(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_COUNT))
            val seats =
                cursor.getString(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_SEATS))
            val theater =
                cursor.getString(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_THEATER))
            val price =
                cursor.getInt(cursor.getColumnIndexOrThrow(TicketDataContract.TABLE_COLUMN_PRICE))

            val data = BookingMovieModel(
                title,
                MovieDateDto(LocalDate.parse(date)),
                MovieTimeDto(LocalTime.parse(time)),
                TicketCountDto(count),
                seats,
                theater,
                price
            )
            BookingHistoryDto.add(data)
        }
        cursor.close()
    }

    fun closeDB() {
        writableDB.close()
    }
}
