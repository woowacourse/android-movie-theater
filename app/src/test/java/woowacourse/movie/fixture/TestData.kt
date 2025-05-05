package woowacourse.movie.fixture

import woowacourse.movie.R
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.RunningTime
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Seat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object TestData {
    val movie =
        Movie(
            title = "해리 포터와 마법사의 돌",
            runningTime = RunningTime(152),
            poster = R.drawable.harrypotter_1.toString(),
            startDate = LocalDate.of(2025, 5, 1),
            endDate = LocalDate.of(2025, 5, 25),
        )

    val screening =
        Screening(
            listOf(LocalTime.of(9, 0, 0), LocalTime.of(9, 0, 0)),
            Cinema(1, "잠실"),
            movie,
        )

    val reservationInfo =
        ReservationInfo(
            movie.title,
            LocalDateTime.of(2025, 5, 1, 9, 0, 0),
            ReservationCount(2),
            mutableListOf(Seat(0, 1), Seat(3, 1)),
            cinema = Cinema(1, "잠실, 극장"),
        )
}
