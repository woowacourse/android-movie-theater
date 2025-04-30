package woowacourse.movie.domain.model

import woowacourse.movie.sample.DUMMY_MOVIES
import java.time.LocalDate
import java.time.LocalTime

val TEST_DUMMY_MOVIES =
    listOf(
        Movie(
            "해리 포터와 마법사의 돌",
            0,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 5, 30),
            ),
            152,
        ),
        Movie(
            "해리 포터와 비밀의 방",
            0,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 5, 28),
            ),
            162,
        ),
        Movie(
            "해리 포터와 아즈카반의 죄수",
            0,
            ScreeningPeriod(
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 31),
            ),
            141,
        ),
        Movie(
            "해리 포터와 불의 잔",
            0,
            ScreeningPeriod(
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
            157,
        ),
    )

val TEST_MOVIE_SCHEDULES = listOf(
    MovieSchedule(
        movie = TEST_DUMMY_MOVIES.first(),
        screeningDateTime =
            ScreeningDateTime(
                screeningDate = LocalDate.of(2025, 4, 10),
                screeningTime = LocalTime.of(13, 0),
            ),
        seat = Seats(),
    ),
    MovieSchedule(
        movie = TEST_DUMMY_MOVIES.first(),
        screeningDateTime =
            ScreeningDateTime(
                screeningDate = LocalDate.of(2025, 4, 15),
                screeningTime = LocalTime.of(16, 30),
            ),
        seat = Seats(),
    )
)


val TEST_DUMMY_THEATERS =
    Theaters(
        theaters =
            listOf(
                Theater(
                    name = "CGV 강남",
                    movieSchedules =
                        listOf(
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[0],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 4, 10),
                                        screeningTime = LocalTime.of(13, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[1],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 4, 15),
                                        screeningTime = LocalTime.of(16, 30),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[2],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 5, 3),
                                        screeningTime = LocalTime.of(22, 0),
                                    ),
                                seat = Seats(),
                            ),
                        ),
                ),
                Theater(
                    name = "롯데시네마 건대입구",
                    movieSchedules =
                        listOf(
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[1],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 4, 20),
                                        screeningTime = LocalTime.of(12, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[1],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 5, 1),
                                        screeningTime = LocalTime.of(12, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[1],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 5, 1),
                                        screeningTime = LocalTime.of(23, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[2],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 5, 10),
                                        screeningTime = LocalTime.of(18, 45),
                                    ),
                                seat = Seats(),
                            ),
                        ),
                ),
                Theater(
                    name = "메가박스 코엑스",
                    movieSchedules =
                        listOf(
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[2],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 5, 5),
                                        screeningTime = LocalTime.of(11, 30),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[3],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 6, 1),
                                        screeningTime = LocalTime.of(19, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = TEST_DUMMY_MOVIES[3],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 6, 1),
                                        screeningTime = LocalTime.of(22, 0),
                                    ),
                                seat = Seats(),
                            ),
                        ),
                ),
            ),
    )
