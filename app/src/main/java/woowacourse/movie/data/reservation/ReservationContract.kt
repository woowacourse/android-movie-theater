package woowacourse.movie.data.reservation

import android.provider.BaseColumns

object ReservationContract {

    object ReservationEntry : BaseColumns {
        const val TABLE_NAME = "Reservation"
        const val COLUMN_NAME_MOVIE_TITLE = "movie_title"
        const val COLUMN_NAME_MOVIE_RUNNING_TIME = "movie_running_time"
        const val COLUMN_NAME_MOVIE_SUMMARY = "movie_summary"
        const val COLUMN_NAME_SCREENING_DATE_TIME = "screening_date_time"
        const val COLUMN_NAME_THEATER_ID = "theater_id"
    }

    object ReservationSeatsEntry {
        const val TABLE_NAME = "Reservation_Seats"
        const val COLUMN_NAME_RESERVATION_ID = "reservation_id"
        const val COLUMN_NAME_ROW = "row"
        const val COLUMN_NAME_COLUMN = "column"
    }
}