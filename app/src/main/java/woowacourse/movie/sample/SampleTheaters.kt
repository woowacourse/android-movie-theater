package woowacourse.movie.sample

import woowacourse.movie.R
import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TheaterSchedules
import woowacourse.movie.domain.model.Theaters
import java.time.LocalDateTime

val DUMMY_ADS =
    listOf(
        Advertisement(
            R.drawable.woowacourse,
        ),
    )

val DUMMY_THEATERS =
    Theaters(
        theaters =
            listOf(
                Theater(
                    name = "CGV 강남",
                    theaterSchedules =
                        TheaterSchedules(
                            mutableMapOf(
                                1L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 4, 10, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 7, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 13, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 17, 0)),
                                    ),
                                2L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 20, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 17, 0)),
                                    ),
                                4L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 15, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 16, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 17, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 17, 20, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 30, 17, 0)),
                                    ),
                            ),
                        ),
                ),
                Theater(
                    name = "롯데시네마 건대입구",
                    theaterSchedules =
                        TheaterSchedules(
                            mutableMapOf(
                                1L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 4, 20, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 7, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 7, 13, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 17, 0)),
                                    ),
                                4L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 20, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 17, 0)),
                                    ),
                                5L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 6, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 6, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 9, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 9, 20, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 9, 23, 0)),
                                    ),
                            ),
                        ),
                ),
                Theater(
                    name = "메가박스 코엑스",
                    theaterSchedules =
                        TheaterSchedules(
                            mutableMapOf(
                                3L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 1, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 7, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 7, 13, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 17, 0)),
                                    ),
                                6L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 8, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 9, 20, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 10, 15, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 11, 17, 0)),
                                    ),
                                8L to
                                    setOf(
                                        MovieSchedule(LocalDateTime.of(2025, 5, 6, 11, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 6, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 11, 16, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 11, 20, 0)),
                                        MovieSchedule(LocalDateTime.of(2025, 5, 11, 23, 0)),
                                    ),
                            ),
                        ),
                ),
            ),
    )
