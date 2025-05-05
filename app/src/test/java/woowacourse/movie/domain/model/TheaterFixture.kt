package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

val MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE =
    Movie(
        1L,
        "해리 포터와 마법사의 돌",
        0,
        ScreeningPeriod(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 5, 30),
        ),
        152,
    )

val MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS =
    Movie(
        2L,
        "해리 포터와 비밀의 방",
        0,
        ScreeningPeriod(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 5, 28),
        ),
        162,
    )

val MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN =
    Movie(
        3L,
        "해리 포터와 아즈카반의 죄수",
        0,
        ScreeningPeriod(
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 31),
        ),
        141,
    )

val MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE =
    Movie(
        4L,
        "해리 포터와 불의 잔",
        0,
        ScreeningPeriod(
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 30),
        ),
        157,
    )

val THEATER_CGV_GANGNAM =
    Theater(
        name = "CGV 강남",
        theaterSchedules =
            TheaterSchedules(
                mutableMapOf(
                    1L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 4, 10, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 4, 10, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 4, 10, 17, 0)),
                        ),
                    2L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 4, 10, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 20, 17, 0)),
                        ),
                    4L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 5, 1, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 2, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 3, 17, 0)),
                        ),
                ),
            ),
    )

val THEATER_LOTTE_SINEMA =
    Theater(
        name = "롯데시네마 건대입구",
        theaterSchedules =
            TheaterSchedules(
                mutableMapOf(
                    1L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 4, 20, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 10, 17, 0)),
                        ),
                    2L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 4, 10, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 20, 17, 0)),
                        ),
                    3L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 5, 11, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 12, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 13, 17, 0)),
                        ),
                ),
            ),
    )

val THEATER_MEGA_BOX =
    Theater(
        name = "메가박스 코엑스",
        theaterSchedules =
            TheaterSchedules(
                mutableMapOf(
                    1L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 5, 8, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 9, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 9, 17, 0)),
                        ),
                    3L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 5, 10, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 12, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 23, 17, 0)),
                        ),
                    4L to
                        setOf(
                            MovieSchedule(LocalDateTime.of(2025, 5, 1, 13, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 2, 15, 0)),
                            MovieSchedule(LocalDateTime.of(2025, 5, 3, 17, 0)),
                        ),
                ),
            ),
    )

val TEST_DUMMY_THEATERS =
    Theaters(theaters = listOf(THEATER_CGV_GANGNAM, THEATER_LOTTE_SINEMA, THEATER_MEGA_BOX))
