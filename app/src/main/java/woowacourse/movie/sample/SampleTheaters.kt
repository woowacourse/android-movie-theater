package woowacourse.movie.sample

import woowacourse.movie.R
import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.ScreeningDateTime
import woowacourse.movie.domain.model.ScreeningPeriod
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters
import java.time.LocalDate
import java.time.LocalTime

val DUMMY_ADS =
    listOf(
        Advertisement(
            R.drawable.woowacourse,
        ),
    )

val DUMMY_MOVIES =
    listOf(
        Movie(
            "해리 포터와 마법사의 돌",
            R.drawable.harry_potter_one,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 5, 30),
            ),
            152,
        ),
        Movie(
            "해리 포터와 비밀의 방",
            R.drawable.harry_potter_two,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 5, 28),
            ),
            162,
        ),
        Movie(
            "해리 포터와 아즈카반의 죄수",
            R.drawable.harry_potter_three,
            ScreeningPeriod(
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 31),
            ),
            141,
        ),
        Movie(
            "해리 포터와 불의 잔",
            R.drawable.harry_potter_four,
            ScreeningPeriod(
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
            157,
        ),
    )

val DUMMY_THEATERS =
    Theaters(
        theaters =
            listOf(
                Theater(
                    name = "CGV 강남",
                    movieSchedules =
                        listOf(
                            MovieSchedule(
                                movie = DUMMY_MOVIES[0],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 4, 10),
                                        screeningTime = LocalTime.of(13, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = DUMMY_MOVIES[1],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 4, 15),
                                        screeningTime = LocalTime.of(16, 30),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = DUMMY_MOVIES[2],
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
                                movie = DUMMY_MOVIES[1],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 4, 20),
                                        screeningTime = LocalTime.of(12, 0),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = DUMMY_MOVIES[2],
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
                                movie = DUMMY_MOVIES[2],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 5, 5),
                                        screeningTime = LocalTime.of(11, 30),
                                    ),
                                seat = Seats(),
                            ),
                            MovieSchedule(
                                movie = DUMMY_MOVIES[3],
                                screeningDateTime =
                                    ScreeningDateTime(
                                        screeningDate = LocalDate.of(2025, 6, 1),
                                        screeningTime = LocalTime.of(19, 0),
                                    ),
                                seat = Seats(),
                            ),
                        ),
                ),
            ),
    )
