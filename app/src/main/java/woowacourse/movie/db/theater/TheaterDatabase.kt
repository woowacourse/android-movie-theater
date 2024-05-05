package woowacourse.movie.db.theater

import woowacourse.movie.model.theater.ScreeningSlot
import woowacourse.movie.model.theater.Theater
import java.time.LocalTime

object TheaterDatabase {
    private val seolleungScreeningSchedule =
        listOf(
            ScreeningSlot(
                screeningTime = LocalTime.of(9, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(10, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(11, 0),
                movieId = 1,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(12, 0),
                movieId = 2,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(13, 0),
                movieId = 3,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(14, 0),
                movieId = 4,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(15, 0),
                movieId = 4,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(16, 0),
                movieId = 5,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(17, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(18, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(19, 0),
                movieId = 7,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(20, 0),
                movieId = 7,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(21, 0),
                movieId = 8,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(22, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(23, 0),
                movieId = 0,
            ),
        )

    private val jamsilScreeningSchedule =
        listOf(
            ScreeningSlot(
                screeningTime = LocalTime.of(9, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(10, 0),
                movieId = 8,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(11, 0),
                movieId = 2,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(12, 0),
                movieId = 3,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(13, 0),
                movieId = 3,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(14, 0),
                movieId = 5,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(15, 0),
                movieId = 5,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(16, 0),
                movieId = 5,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(17, 0),
                movieId = 1,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(18, 0),
                movieId = 1,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(19, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(20, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(21, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(22, 0),
                movieId = 0,
            ),
        )

    private val gangnamScreeningSchedule =
        listOf(
            ScreeningSlot(
                screeningTime = LocalTime.of(9, 0),
                movieId = 8,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(10, 0),
                movieId = 1,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(11, 0),
                movieId = 4,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(12, 0),
                movieId = 4,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(13, 0),
                movieId = 8,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(14, 0),
                movieId = 8,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(15, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(16, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(17, 0),
                movieId = 6,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(18, 0),
                movieId = 7,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(19, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(20, 0),
                movieId = 0,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(21, 0),
                movieId = 2,
            ),
            ScreeningSlot(
                screeningTime = LocalTime.of(22, 0),
                movieId = 2,
            ),
        )

    val theaters: List<Theater> =
        listOf(
            Theater(
                theaterId = 0,
                name = "선릉 극장",
                seolleungScreeningSchedule,
            ),
            Theater(
                theaterId = 1,
                name = "잠실 극장",
                jamsilScreeningSchedule,
            ),
            Theater(
                theaterId = 2,
                name = "강남 극장",
                gangnamScreeningSchedule,
            ),
        )
}
