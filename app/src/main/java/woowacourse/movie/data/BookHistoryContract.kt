package woowacourse.movie.data

import android.provider.BaseColumns

object BookHistoryContract : BaseColumns {
    const val DATABASE_NAME = "MovieBookHistory_db"
    const val TABLE_NAME = "movieHistory"
    const val TABLE_COLUMN_TITLE = "movieTitle"
    const val TABLE_COLUMN_DATE = "movieDate"
    const val TABLE_COLUMN_NUMBER_OF_PEOPLE = "movieNumberOfPeople"
    const val TABLE_COLUMN_SEATS = "bookedSeats"
    const val TABLE_COLUMN_PRICE = "totalPrice"
}
