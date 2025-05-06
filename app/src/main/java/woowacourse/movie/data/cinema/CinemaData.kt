package woowacourse.movie.data.cinema

import woowacourse.movie.data.reservation.LocalScreeningData
import woowacourse.movie.data.reservation.ScreeningData
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDateTime
import java.time.LocalTime

interface CinemaData {
    val value: List<Cinema>
}

class LocalCinemaData(
    screeningData: ScreeningData = LocalScreeningData(),
) : CinemaData {
    override val value: List<Cinema> =
        listOf(
            Cinema(
                "선릉 극장",
                screeningData.value,
                object : ShowtimePolicy() {
                    override fun showtimes(current: LocalDateTime): List<LocalTime> =
                        listOf(
                            LocalTime.of(11, 0),
                            LocalTime.of(14, 0),
                            LocalTime.of(17, 0),
                            LocalTime.of(20, 0),
                            LocalTime.of(23, 0),
                        ).filter { showtime: LocalTime -> showtime.isAfter(current.toLocalTime()) }
                            .sorted()
                },
            ),
            Cinema(
                "잠실 극장",
                screeningData.value,
                object : ShowtimePolicy() {
                    override fun showtimes(current: LocalDateTime): List<LocalTime> =
                        listOf(
                            LocalTime.of(9, 0),
                            LocalTime.of(10, 0),
                            LocalTime.of(11, 0),
                            LocalTime.of(12, 0),
                            LocalTime.of(13, 0),
                            LocalTime.of(14, 0),
                            LocalTime.of(15, 0),
                            LocalTime.of(16, 0),
                            LocalTime.of(22, 0),
                            LocalTime.of(23, 0),
                        ).filter { showtime: LocalTime -> showtime.isAfter(current.toLocalTime()) }
                            .sorted()
                },
            ),
            Cinema(
                "강남 극장",
                screeningData.value,
                object : ShowtimePolicy() {
                    override fun showtimes(current: LocalDateTime): List<LocalTime> =
                        listOf(
                            LocalTime.of(10, 0),
                            LocalTime.of(9, 0),
                            LocalTime.of(6, 0),
                        ).filter { showtime: LocalTime -> !showtime.isBefore(current.toLocalTime()) }
                            .sorted()
                },
            ),
        )
}

class FakeCinemaData(
    override val value: List<Cinema>,
) : CinemaData
