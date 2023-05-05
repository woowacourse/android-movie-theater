package com.woowacourse.data.database.reservation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.woowacourse.data.database.reservation.history.contract.HistoryContract
import com.woowacourse.data.database.reservation.seat.SeatContract

class ReservationDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(HistoryContract.Query.SQL_CREATE_TABLE)
        db?.execSQL(SeatContract.Query.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(HistoryContract.Query.SQL_DELETE_TABLE)
        db?.execSQL(SeatContract.Query.SQL_DELETE_TABLE)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "Reservation.db"
    }
}
