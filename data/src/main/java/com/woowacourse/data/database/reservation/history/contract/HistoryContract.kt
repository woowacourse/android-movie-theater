package com.woowacourse.data.database.reservation.history.contract

import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_DATE
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_MOVIE_TITLE
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_THEATER_NAME
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_TICKET_COUNT
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_TIME
import com.woowacourse.data.database.reservation.history.contract.HistoryContract.Entry.COLUMN_TOTAL_PRICE

object HistoryContract {
    object Query {
        internal const val SQL_CREATE_TABLE =
            "CREATE TABLE ${Entry.TABLE_NAME} (" +
                "$_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_MOVIE_TITLE TEXT," +
                "$COLUMN_THEATER_NAME TEXT," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_TIME TEXT," +
                "$COLUMN_TICKET_COUNT INTEGER," +
                "$COLUMN_TOTAL_PRICE INTEGER)"

        internal const val SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS ${Entry.TABLE_NAME}"
    }

    object Entry : BaseColumns {
        internal const val TABLE_NAME = "history"
        internal const val COLUMN_MOVIE_TITLE = "title"
        internal const val COLUMN_THEATER_NAME = "theater"
        internal const val COLUMN_DATE = "selected_date"
        internal const val COLUMN_TIME = "selected_time"
        internal const val COLUMN_TICKET_COUNT = "ticket_count"
        internal const val COLUMN_TOTAL_PRICE = "total_price"
    }
}
