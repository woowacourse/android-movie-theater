package com.woowacourse.data.database.reservation.seat

import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import com.woowacourse.data.database.reservation.history.contract.HistoryContract
import com.woowacourse.data.database.reservation.seat.SeatContract.Entry.COLUMN_COL
import com.woowacourse.data.database.reservation.seat.SeatContract.Entry.COLUMN_MOVIE_ID
import com.woowacourse.data.database.reservation.seat.SeatContract.Entry.COLUMN_ROW

object SeatContract {
    object Query {
        internal const val SQL_CREATE_TABLE =
            "CREATE TABLE ${Entry.TABLE_NAME} (" +
                "$_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_MOVIE_ID INTEGER," +
                "$COLUMN_ROW INTEGER," +
                "$COLUMN_COL INTEGER)"

        internal const val SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS ${Entry.TABLE_NAME}"
    }

    object Entry : BaseColumns {
        internal const val TABLE_NAME = "seat"
        internal const val COLUMN_MOVIE_ID =
            "${HistoryContract.Entry.TABLE_NAME}${BaseColumns._ID}"
        internal const val COLUMN_ROW = "row"
        internal const val COLUMN_COL = "col"
    }
}
