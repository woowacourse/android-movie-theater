package woowacourse.movie.util

enum class MovieIntent(val key: String, val invalidValue: Any) {
    MOVIE_ID("movieId", -1L),
    MOVIE_DATE("movieDate", ""),
    MOVIE_TIME("movieTime", ""),
    RESERVATION_COUNT("reservationCount", -1),
    MOVIE_SEATS("movieSeats", ""),
    ITEM_POSITION("itemPosition", -1),
    SELECTED_SEAT_POSITIONS("selectedSeatPositions", false),
    SELECTED_THEATER_POSITION("selectedTheaterPosition", -1),
    IS_GRANTED("isGranted", false),
}
