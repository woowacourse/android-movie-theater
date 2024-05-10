package woowacourse.movie.util

object MovieIntentConstant {
    const val KEY_MOVIE_ID = "movieId"
    const val KEY_RESERVATION_COUNT = "reservationCount"
    const val KEY_SELECTED_SEAT_POSITIONS = "selectedSeatPositions"
    const val KEY_SELECTED_THEATER_INDEX = "selectedTheaterIndex"
    const val KEY_TICKET_ID = "movieId"
    const val KEY_NOTIFICATION = "notification"
    const val KEY_NOTIFICATION_TITLE = "notification_title"
    const val KEY_NOTIFICATION_DESCRIPTION = "notification_description"
    const val KEY_RESERVATION_ID = "reservationId"

    const val INVALID_VALUE_MOVIE_ID: Long = -1
    const val INVALID_VALUE_RESERVATION_COUNT: Int = -1
    const val INVALID_VALUE_THEATER_INDEX: Int = -1
    const val INVALID_VALUE_TICKET_ID: Long = -1
    const val DEFAULT_VALUE_NOTIFICATION: Boolean = false
    const val DEFAULT_VALUE_NOTIFICATION_TITLE: String = ""
    const val DEFAULT_VALUE_NOTIFICATION_DESCRIPTION: String = ""
    const val DEFAULT_VALUE_RESERVATION_ID: Long = -1L
}
