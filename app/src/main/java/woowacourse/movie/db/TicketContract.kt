package woowacourse.movie.db

import android.provider.BaseColumns

object TicketContract : BaseColumns {
    const val TABLE_NAME = "ticket"
    const val TITLE = "title"
    const val THEATER = "theater"
    const val DATE_TIME = "dateTime"
    const val SEATS = "seats"
}
