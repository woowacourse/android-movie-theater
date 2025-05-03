package woowacourse.movie.fixture

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
            LocalDate.of(2025, 5, 1),
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

val SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_10_1300 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE,
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 4, 10),
                time = LocalTime.of(13, 0),
            ),
        seat = Seats(),
    )
val SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_15_1630 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE,
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 4, 15),
                time = LocalTime.of(16, 30),
            ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_15_1630 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 4, 15),
                time = LocalTime.of(16, 30),
            ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_03_2200 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 5, 3),
                time = LocalTime.of(22, 0),
            ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_20_1200 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 4, 20),
            time = LocalTime.of(12, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_05_01_1200 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 5, 1),
            time = LocalTime.of(12, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_05_01_2300 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 5, 1),
            time = LocalTime.of(23, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_01_2300 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 5, 1),
            time = LocalTime.of(23, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_05_1130 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 5, 1),
            time = LocalTime.of(23, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_GOBLET_OF_FIRE_2025_06_01_1900 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 6, 1),
            time = LocalTime.of(19, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_GOBLET_OF_FIRE_2025_06_01_2200 =
    Schedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE,
        ScreeningTimeSchedule(
            date = LocalDate.of(2025, 6, 1),
            time = LocalTime.of(22, 0),
        ),
        seat = Seats(),
    )

val TEST_MOVIE_SCHEDULES =
    listOf(
        SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_10_1300,
        SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_15_1630,
    )

val THEATER_CGV_GANGNAM =
    Theater(
        name = "CGV 강남",
        schedules =
            listOf(
                SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_10_1300,
                SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_15_1630,
                SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_03_2200,
            ),
    )

val THEATER_LOTTE_SINEMA =
    Theater(
        name = "롯데시네마 건대입구",
        schedules =
            listOf(
                SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_20_1200,
                SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_05_01_1200,
                SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_05_01_2300,
                SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_01_2300,
            ),
    )

val THEATER_MEGA_BOX =
    Theater(
        name = "메가박스 코엑스",
        schedules =
            listOf(
                SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_05_1130,
                SCHEDULE_HARRY_THE_GOBLET_OF_FIRE_2025_06_01_1900,
                SCHEDULE_HARRY_THE_GOBLET_OF_FIRE_2025_06_01_2200,
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
