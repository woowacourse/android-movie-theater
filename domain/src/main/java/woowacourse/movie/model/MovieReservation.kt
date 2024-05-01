package woowacourse.movie.model

import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class MovieReservation(
    val id: Long,
    val movie: Movie,
    val reserveSeats: ReserveSeats,
    val screenDateTime: LocalDateTime,
    val headCount: HeadCount,
    val cancelDeadLine: Duration = 15.minutes,
    val theaterId: Long,
) {
    constructor(
        id: Long,
        screeningMovie: ScreeningMovie,
        screenDateTime: LocalDateTime,
        reserveSeats: ReserveSeats,
        headCount: HeadCount,
        cancelDeadLine: Duration = 15.minutes,
        theaterId: Long,
    ) : this(
        id,
        screeningMovie.movie,
        reserveSeats,
        screenDateTime,
        headCount,
        cancelDeadLine,
        theaterId,
    )

    val totalPrice: Price get() = reserveSeats.totalPrice
}
