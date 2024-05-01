package woowacourse.movie.db.theater

import woowacourse.movie.model.Theater
import woowacourse.movie.model.movie.ScreeningTimes
import java.time.LocalTime

object TheaterDatabase {
    val theaters: List<Theater> =
        listOf(
            Theater(
                theaterId = 0,
                name = "선릉 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                                LocalTime.of(16, 0),
                            ),
                    ),
                movieId = 0,
            ),
            Theater(
                theaterId = 1,
                name = "잠실 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(9, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                            ),
                    ),
                movieId = 1,
            ),
            Theater(
                theaterId = 2,
                name = "강남 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                                LocalTime.of(16, 0),
                            ),
                    ),
                movieId = 2,
            ),
            Theater(
                theaterId = 3,
                name = "선릉 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                                LocalTime.of(16, 0),
                            ),
                    ),
                movieId = 2,
            ),
            Theater(
                theaterId = 4,
                name = "잠실 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(9, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                            ),
                    ),
                movieId = 1,
            ),
            Theater(
                theaterId = 5,
                name = "강남 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                                LocalTime.of(16, 0),
                            ),
                    ),
                movieId = 0,
            ),
            Theater(
                theaterId = 6,
                name = "잠실 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(9, 0),
                            ),
                        weekEnd =
                            listOf(
                                LocalTime.of(14, 0),
                            ),
                    ),
                movieId = 0,
            ),
        )
}
