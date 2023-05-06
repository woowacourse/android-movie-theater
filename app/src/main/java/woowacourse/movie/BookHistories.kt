package woowacourse.movie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.movie.MovieBookingSeatInfo

object BookHistories {
    val items = mutableListOf<MovieBookingSeatInfo>()
    fun getDBInstance(context: Context): SQLiteDatabase =
        BookingHistoryDBHelper(context).writableDatabase
}
