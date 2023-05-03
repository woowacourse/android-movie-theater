package com.woowacourse.data.database.reservation.history.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
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

class HistoryDao(private val database: ReservationDatabase) {
    fun getAll(): List<DataReservation> {
        TODO("예약 리스트 반환 기능 구현")
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
        historyId: Long
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
