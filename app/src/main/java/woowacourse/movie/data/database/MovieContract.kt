package woowacourse.movie.data.database

import android.provider.BaseColumns

object MovieContract {
    object Booking : BaseColumns {
        const val TABLE_NAME = "booking"
        const val MOVIE_TITLE = "movieTitle"
        const val DATE = "date"
        const val TIME = "time"
        const val PERSON_COUNT = "personCount"
        const val SEAT_NAMES = "seatNames"
        const val TOTAL_PRICE = "totalPrice"
    }
}