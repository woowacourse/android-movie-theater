package woowacourse.movie

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Schedule
import woowacourse.movie.model.Theater
import java.time.LocalDate
import java.time.LocalTime

object DefaultMovieData {
    fun mockTheaterList(): List<Theater> {
        return listOf(
            Theater(
                place = "선릉",
                schedules =
                    listOf(
                        Schedule(
                            Movie(
                                title = "해리 포터와 마법사의 돌",
                                imageSource = "harry_potter.png",
                                screeningStartDate = LocalDate.of(2025, 4, 1),
                                screeningEndDate = LocalDate.of(2025, 5, 30),
                                runningTime = 152,
                            ),
                            listOf(LocalTime.of(11, 0), LocalTime.of(15, 0)),
                        ),
                        Schedule(
                            Movie(
                                title = "해리 포터와 불의 잔",
                                imageSource = "harry_potter4.png",
                                screeningStartDate = LocalDate.of(2025, 6, 1),
                                screeningEndDate = LocalDate.of(2025, 6, 30),
                                runningTime = 157,
                            ),
                            listOf(LocalTime.of(12, 0)),
                        ),
                    ),
            ),
            Theater(
                place = "잠실",
                schedules =
                    mockMovieList().map {
                        Schedule(
                            it,
                            screeningTimes =
                                listOf(
                                    LocalTime.of(11, 0),
                                    LocalTime.of(12, 0),
                                    LocalTime.of(13, 0),
                                    LocalTime.of(16, 0),
                                    LocalTime.of(23, 0),
                                ),
                        )
                    },
            ),
            Theater(
                place = "강남",
                schedules =
                    mockMovieList().map {
                        Schedule(
                            it,
                            screeningTimes =
                                listOf(
                                    LocalTime.of(11, 0),
                                    LocalTime.of(12, 0),
                                    LocalTime.of(13, 0),
                                    LocalTime.of(16, 0),
                                    LocalTime.of(23, 0),
                                ),
                        )
                    },
            ),
        )
    }

    private fun mockMovieList(): List<Movie> {
        return listOf(
            Movie(
                title = "해리 포터와 마법사의 돌",
                imageSource = "harry_potter.png",
                screeningStartDate = LocalDate.of(2025, 4, 1),
                screeningEndDate = LocalDate.of(2025, 5, 30),
                runningTime = 152,
            ),
            Movie(
                title = "해리 포터와 비밀의 방",
                imageSource = "harry_potter2.png",
                screeningStartDate = LocalDate.of(2025, 4, 1),
                screeningEndDate = LocalDate.of(2025, 4, 30),
                runningTime = 162,
            ),
            Movie(
                title = "해리 포터와 아즈카반의 죄수",
                imageSource = "harry_potter3.png",
                screeningStartDate = LocalDate.of(2025, 5, 1),
                screeningEndDate = LocalDate.of(2025, 5, 30),
                runningTime = 141,
            ),
            Movie(
                title = "해리 포터와 불의 잔",
                imageSource = "harry_potter4.png",
                screeningStartDate = LocalDate.of(2025, 6, 1),
                screeningEndDate = LocalDate.of(2025, 6, 30),
                runningTime = 157,
            ),
            Movie(
                title = "스타 이즈 본",
                imageSource = "star_is_born.jpg",
                screeningStartDate = LocalDate.of(2025, 4, 19),
                screeningEndDate = LocalDate.of(2025, 5, 30),
                runningTime = 135,
            ),
        )
    }
}
