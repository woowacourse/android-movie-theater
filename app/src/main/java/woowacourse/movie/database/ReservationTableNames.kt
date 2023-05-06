package woowacourse.movie.database

import android.provider.BaseColumns

object ReservationTableNames : BaseColumns {
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_NAME = "reservation"
    const val TABLE_COLUMN_DATE = "date"
    const val TABLE_COLUMN_COUNT = "people_count"
    const val TABLE_COLUMN_SEATS = "seats"
    const val TABLE_COLUMN_PRICE = "price"
}
