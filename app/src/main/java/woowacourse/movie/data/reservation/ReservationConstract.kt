package woowacourse.movie.data.reservation

import android.provider.BaseColumns

object ReservationConstract : BaseColumns {
    const val TABLE_NAME = "reservation"
    const val TABLE_COLUMN_TITLE = "title"
    const val TABLE_COLUMN_SCREENING_TIME = "screening_time"
    const val TABLE_COLUMN_SEATS = "seats"
    const val TABLE_COLUMN_FEE = "fee"
}
