package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import woowacourse.movie.domain.model.theater.Schedule
import woowacourse.movie.domain.model.theater.ScreeningTimeSchedule
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalTime

val MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE =
    Movie(
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
        "해리 포터와 아즈카반의 죄수",
        0,
        ScreeningPeriod(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 5, 31),
        ),
        141,
    )

val MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE =
    Movie(
        "해리 포터와 불의 잔",
        0,
        ScreeningPeriod(
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 30),
        ),
        157,
    )

val TEST_DUMMY_MOVIES =
    listOf(
        MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE,
        MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE,
    )

val SCHEDULE_2025_04_10_TIME_1300 =
    Schedule(
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 4, 10),
                time =
                    listOf(
                        LocalTime.of(13, 0),
                    ),
            ),
        seat = Seats(),
    )
val SCHEDULE_2025_04_15_TIME_1630 =
    Schedule(
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 4, 15),
                time =
                    listOf(
                        LocalTime.of(16, 30),
                    ),
            ),
        seat = Seats(),
    )

val SCHEDULE_2025_05_15_TIME_1330_1630 =
    Schedule(
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 5, 15),
                time =
                    listOf(
                        LocalTime.of(13, 30),
                        LocalTime.of(16, 30),
                    ),
            ),
        seat = Seats(),
    )

val SCHEDULE_2025_05_16_TIME_1400_1700 =
    Schedule(
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 5, 16),
                time =
                    listOf(
                        LocalTime.of(14, 0),
                        LocalTime.of(17, 0),
                    ),
            ),
        seat = Seats(),
    )

val THEATER_CGV_GANGNAM =
    Theater(
        name = "CGV 강남",
        allSchedules =
            mapOf(
                MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE to
                    listOf(
                        SCHEDULE_2025_04_15_TIME_1630,
                    ),
                MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN to
                    listOf(
                        SCHEDULE_2025_04_10_TIME_1300,
                    ),
            ),
    )

val THEATER_LOTTE_SINEMA =
    Theater(
        name = "롯데 시네마 건대 입구",
        allSchedules =
            mapOf(
                MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS to
                    listOf(
                        SCHEDULE_2025_05_15_TIME_1330_1630,
                    ),
            ),
    )

val THEATER_MEGA_BOX =
    Theater(
        name = "메가박스 코엑스",
        allSchedules =
            mapOf(
                MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS to
                    listOf(
                        SCHEDULE_2025_05_16_TIME_1400_1700,
                    ),
            ),
    )

val TEST_DUMMY_THEATERS =
    Theaters(
        theaters =
            listOf(
                THEATER_CGV_GANGNAM,
                THEATER_LOTTE_SINEMA,
                THEATER_MEGA_BOX,
            ),
    )
