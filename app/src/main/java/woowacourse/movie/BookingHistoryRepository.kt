package woowacourse.movie

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.BookingHistoryDBHelper.Companion.KEY_DATE
import woowacourse.movie.BookingHistoryDBHelper.Companion.KEY_SEATS
import woowacourse.movie.BookingHistoryDBHelper.Companion.KEY_TICKET_COUNT
import woowacourse.movie.BookingHistoryDBHelper.Companion.KEY_TIME
import woowacourse.movie.BookingHistoryDBHelper.Companion.KEY_TITLE
import woowacourse.movie.BookingHistoryDBHelper.Companion.KEY_TOTAL_PRICE
import woowacourse.movie.BookingHistoryDBHelper.Companion.TABLE_NAME
import woowacourse.movie.movie.MovieBookingInfo
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookingHistoryRepository(private val database: SQLiteDatabase) {
    fun getAll(): List<MovieBookingSeatInfo> {
        val bookingInfoGroup = mutableListOf<MovieBookingSeatInfo>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val movieBookingInfo = MovieBookingInfo(
                    title = it.getString(it.getColumnIndexOrThrow(KEY_TITLE)),
                    date = it.getString(it.getColumnIndexOrThrow(KEY_DATE)),
                    time = it.getString(it.getColumnIndexOrThrow(KEY_TIME)),
                    ticketCount = it.getInt(it.getColumnIndexOrThrow(KEY_TICKET_COUNT))
                )
                bookingInfoGroup.add(
                    MovieBookingSeatInfo(
                        movieBookingInfo = movieBookingInfo,
                        seats = it.getString(it.getColumnIndexOrThrow(KEY_SEATS)).chunked(2),
                        totalPrice = it.getString(it.getColumnIndexOrThrow(KEY_TOTAL_PRICE))
                    )
                )
            }
        }
        return bookingInfoGroup
    }

    fun insert(movieBookingSeatInfo: MovieBookingSeatInfo) {
        val record = ContentValues().apply {
            put(KEY_TITLE, movieBookingSeatInfo.movieBookingInfo.title)
            put(KEY_TIME, movieBookingSeatInfo.movieBookingInfo.time)
            put(KEY_DATE, movieBookingSeatInfo.movieBookingInfo.date)
            put(KEY_TICKET_COUNT, movieBookingSeatInfo.movieBookingInfo.ticketCount)
            put(KEY_SEATS, movieBookingSeatInfo.seats.joinToString(""))
            put(KEY_TOTAL_PRICE, movieBookingSeatInfo.totalPrice)
        }
        database.insert(TABLE_NAME, null, record)
    }

    fun clear() {
        database.execSQL("DELETE FROM $TABLE_NAME")
    }

    fun isEmpty(): Boolean =
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use { it.count == 0 }

    fun close() {
        database.close()
    }
}
