package com.woowacourse.data.database.reservation.history.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.woowacourse.data.database.reservation.ReservationDatabase
import com.woowacourse.data.database.reservation.history.contract.HistoryContract
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_DATE
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_MOVIE_TITLE
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_TICKET_COUNT
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_TIME
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_TOTAL_PRICE
import com.woowacourse.data.database.reservation.seat.SeatContract
import com.woowacourse.data.database.reservation.seat.SeatContract.Entry.COLUMN_COL
import com.woowacourse.data.database.reservation.seat.SeatContract.Entry.COLUMN_MOVIE_ID
import com.woowacourse.data.database.reservation.seat.SeatContract.Entry.COLUMN_ROW
import com.woowacourse.data.model.DataReservation
import com.woowacourse.data.model.MovieDate
import com.woowacourse.data.model.MovieTime
import com.woowacourse.data.model.PickedSeats
import com.woowacourse.data.model.Seat
import com.woowacourse.data.model.Ticket
import com.woowacourse.data.model.TicketPrice

class ReservationDao(context: Context) {
    private val database: ReservationDatabase = ReservationDatabase(context)

    fun getAll(): List<DataReservation> {
        database.readableDatabase.use { db ->
            val historyTable = HistoryContract.Entry.TABLE_NAME
            val cursor = db.rawQuery(
                """
                SELECT * FROM $historyTable
                ORDER BY $historyTable.$_ID ASC
                """.trimIndent(),
                null
            )

            val historyList = mutableListOf<DataReservation>()
            while (cursor.moveToNext()) {
                historyList.add(getHistory(cursor, db))
            }
            return historyList
        }
    }

    @SuppressLint("Range")
    private fun getHistory(cursor: Cursor, db: SQLiteDatabase): DataReservation {
        val historyId = cursor.getInt(cursor.getColumnIndex(_ID))
        val movieTitle = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_TITLE))
        val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
        val time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME))
        val ticketCount = cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_COUNT))
        val totalPrice = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_PRICE))
        val seats = getSeats(db, historyId)
        return DataReservation(
            movieTitle,
            MovieDate.of(date),
            MovieTime.of(time),
            Ticket(ticketCount),
            PickedSeats(seats),
            TicketPrice(totalPrice)
        )
    }

    @SuppressLint("Range")
    private fun getSeats(db: SQLiteDatabase, historyId: Int): List<Seat> {
        val seatTable = SeatContract.Entry.TABLE_NAME
        val cursor = db.rawQuery(
            """
                SELECT * FROM $seatTable
                WHERE $seatTable.$COLUMN_MOVIE_ID = $historyId
            """.trimIndent(),
            null
        )

        val seats = mutableListOf<Seat>()
        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndex(COLUMN_ROW))
            val col = cursor.getInt(cursor.getColumnIndex(COLUMN_COL))
            seats.add(Seat(row, col))
        }
        return seats
    }

    fun add(item: DataReservation) {
        database.writableDatabase.use { db ->
            val historyId: Long = insertMovieHistory(db, item)
            insertSeats(db, item, historyId)
        }
    }

    private fun insertMovieHistory(
        db: SQLiteDatabase,
        item: DataReservation,
    ): Long {
        val movieContentValues = ContentValues().apply {
            put(COLUMN_MOVIE_TITLE, item.movieTitle)
            put(COLUMN_DATE, item.formattedMovieDate)
            put(COLUMN_TIME, item.formattedMovieTime)
            put(COLUMN_TICKET_COUNT, item.ticketCount)
            put(COLUMN_TOTAL_PRICE, item.totalPrice)
        }
        return db.insert(HistoryContract.Entry.TABLE_NAME, null, movieContentValues)
    }

    private fun insertSeats(
        db: SQLiteDatabase,
        item: DataReservation,
        historyId: Long,
    ) {
        item.selectedSeats.forEach { (row, col) ->
            val seatContentValues = ContentValues().apply {
                put(COLUMN_MOVIE_ID, historyId)
                put(COLUMN_ROW, row.value)
                put(COLUMN_COL, col.value)
            }
            db.insert(SeatContract.Entry.TABLE_NAME, null, seatContentValues)
        }
    }
}
