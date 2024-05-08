package woowacourse.movie.db.theater

import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.theater.Theater
import java.time.LocalTime

object TheaterDatabase {
    val theaters: List<Theater> =
        listOf(
            Theater(
                theaterId = 0,
                theaterName = "선릉 극장",
                screeningTimes =
                    ScreeningTimes(
                        weekDay =
                            listOf(
                                LocalTime.of(0, 55),
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
                theaterName = "잠실 극장",
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
                theaterName = "강남 극장",
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
                theaterName = "선릉 극장",
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
                theaterName = "잠실 극장",
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
                theaterName = "강남 극장",
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
                theaterName = "잠실 극장",
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
            Theater(
                theaterId = 0,
                theaterName = "선릉 극장",
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
                movieId = 3,
            ),
            Theater(
                theaterId = 1,
                theaterName = "잠실 극장",
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
                movieId = 4,
            ),
            Theater(
                theaterId = 2,
                theaterName = "강남 극장",
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
                movieId = 5,
            ),
            Theater(
                theaterId = 3,
                theaterName = "선릉 극장",
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
                movieId = 6,
            ),
            Theater(
                theaterId = 4,
                theaterName = "잠실 극장",
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
                movieId = 7,
            ),
            Theater(
                theaterId = 5,
                theaterName = "강남 극장",
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
                movieId = 8,
            ),
        )
}
