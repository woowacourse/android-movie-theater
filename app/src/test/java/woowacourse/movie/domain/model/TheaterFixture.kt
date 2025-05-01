package woowacourse.movie.domain.model

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
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE,
        screeningDateTime =
            ScreeningDateTime(
                screeningDate = LocalDate.of(2025, 4, 10),
                screeningTime = LocalTime.of(13, 0),
            ),
        seat = Seats(),
    )
val SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_15_1630 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE,
        screeningDateTime =
            ScreeningDateTime(
                screeningDate = LocalDate.of(2025, 4, 15),
                screeningTime = LocalTime.of(16, 30),
            ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_15_1630 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        screeningDateTime =
            ScreeningDateTime(
                screeningDate = LocalDate.of(2025, 4, 15),
                screeningTime = LocalTime.of(16, 30),
            ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_03_2200 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        screeningDateTime =
            ScreeningDateTime(
                screeningDate = LocalDate.of(2025, 5, 3),
                screeningTime = LocalTime.of(22, 0),
            ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_20_1200 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 4, 20),
            screeningTime = LocalTime.of(12, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_05_01_1200 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 5, 1),
            screeningTime = LocalTime.of(12, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_05_01_2300 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_CHAMBER_OF_SECRETS,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 5, 1),
            screeningTime = LocalTime.of(23, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_01_2300 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 5, 1),
            screeningTime = LocalTime.of(23, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_05_1130 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 5, 1),
            screeningTime = LocalTime.of(23, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_GOBLET_OF_FIRE_2025_06_01_1900 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 6, 1),
            screeningTime = LocalTime.of(19, 0),
        ),
        seat = Seats(),
    )

val SCHEDULE_HARRY_THE_GOBLET_OF_FIRE_2025_06_01_2200 =
    MovieSchedule(
        movie = MOVIE_HARRY_POTTER_AND_THE_GOBLET_OF_FIRE,
        ScreeningDateTime(
            screeningDate = LocalDate.of(2025, 6, 1),
            screeningTime = LocalTime.of(22, 0),
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
        movieSchedules =
            listOf(
                SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_10_1300,
                SCHEDULE_HARRY_THE_CHAMBER_OF_SECRETS_2025_04_15_1630,
                SCHEDULE_HARRY_THE_PRISONER_OF_AZKABAN_2025_05_03_2200,
            ),
    )

val THEATER_LOTTE_SINEMA =
    Theater(
        name = "롯데시네마 건대입구",
        movieSchedules =
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
        movieSchedules =
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
