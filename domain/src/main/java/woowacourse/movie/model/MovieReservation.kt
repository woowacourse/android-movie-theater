package woowacourse.movie.model

import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class MovieReservation(
    val id: Long,
    val movie: Movie,
    val selectedSeats: SelectedSeats,
    val screenDateTime: LocalDateTime,
    val headCount: HeadCount,
    val cancelDeadLine: Duration = 15.minutes,
    val theaterId: Long,
) {
    constructor(
        id: Long,
        screeningMovie: ScreeningMovie,
        screenDateTime: LocalDateTime,
        selectedSeats: SelectedSeats,
        headCount: HeadCount,
        cancelDeadLine: Duration = 15.minutes,
        theaterId: Long,
    ) : this(
        id,
        screeningMovie.movie,
        selectedSeats,
        screenDateTime,
        headCount,
        cancelDeadLine,
        theaterId,
    )

    val totalPrice: Price get() = selectedSeats.totalPrice

    companion object {
        val STUB =
            MovieReservation(
                0,
                Movie.STUB,
                SelectedSeats(
                    MovieTheater.STUB_A,
                    HeadCount(1),
                    listOf(Seat(SeatRate.B, 0, 0, SeatState.SELECTED)),
                ),
                LocalDateTime.now(),
                HeadCount(1),
                theaterId = 0L,
            )
    }
}
