package woowacourse.movie.sample

import woowacourse.movie.R
import woowacourse.movie.domain.model.item.Advertisement
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import woowacourse.movie.domain.model.theater.Schedule
import woowacourse.movie.domain.model.theater.ScreeningTimeSchedule
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
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
                    allSchedules =
                        mapOf(
                            DUMMY_MOVIES[0] to
                                listOf(
                                    Schedule(
                                        ScreeningTimeSchedule(
                                            date = LocalDate.of(2025, 4, 10),
                                            time =
                                                listOf(
                                                    LocalTime.of(13, 0),
                                                    LocalTime.of(17, 0),
                                                ),
                                        ),
                                        Seats(),
                                    ),
                                    Schedule(
                                        ScreeningTimeSchedule(
                                            date = LocalDate.of(2025, 4, 12),
                                            time =
                                                listOf(
                                                    LocalTime.of(13, 0),
                                                    LocalTime.of(17, 0),
                                                ),
                                        ),
                                        Seats(),
                                    ),
                                ),
                        ),
                ),
                Theater(
                    name = "롯데시네마 건대입구",
                    allSchedules =
                        mapOf(
                            DUMMY_MOVIES[1] to
                                listOf(
                                    Schedule(
                                        ScreeningTimeSchedule(
                                            date = LocalDate.of(2025, 4, 15),
                                            time =
                                                listOf(
                                                    LocalTime.of(14, 0),
                                                    LocalTime.of(18, 30),
                                                ),
                                        ),
                                        Seats(),
                                    ),
                                ),
                            DUMMY_MOVIES[2] to
                                listOf(
                                    Schedule(
                                        ScreeningTimeSchedule(
                                            date = LocalDate.of(2025, 5, 3),
                                            time =
                                                listOf(
                                                    LocalTime.of(15, 0),
                                                ),
                                        ),
                                        Seats(),
                                    ),
                                ),
                        ),
                ),
                Theater(
                    name = "메가박스 코엑스",
                    allSchedules =
                        mapOf(
                            DUMMY_MOVIES[2] to
                                listOf(
                                    Schedule(
                                        ScreeningTimeSchedule(
                                            date = LocalDate.of(2025, 5, 5),
                                            time =
                                                listOf(
                                                    LocalTime.of(12, 0),
                                                    LocalTime.of(16, 0),
                                                ),
                                        ),
                                        Seats(),
                                    ),
                                ),
                            DUMMY_MOVIES[3] to
                                listOf(
                                    Schedule(
                                        ScreeningTimeSchedule(
                                            date = LocalDate.of(2025, 6, 1),
                                            time =
                                                listOf(
                                                    LocalTime.of(13, 30),
                                                ),
                                        ),
                                        Seats(),
                                    ),
                                ),
                        ),
                ),
            ),
    )
