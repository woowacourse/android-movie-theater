package woowacourse.movie.database

import android.provider.BaseColumns

object MovieTableNames : BaseColumns {
    const val TABLE_NAME = "movie"
    const val TABLE_COLUMN_POSTER = "poster"
    const val TABLE_COLUMN_TITLE = "title"
    const val TABLE_COLUMN_START_DATE = "start_date"
    const val TABLE_COLUMN_END_DATE = "end_date"
    const val TABLE_COLUMN_RUNNING_TIME = "running_time"
    const val TABLE_COLUMN_DESCRIPTION = "description"
    const val TABLE_COLUMN_RESERVATION_ID = "reservation_id"
}
