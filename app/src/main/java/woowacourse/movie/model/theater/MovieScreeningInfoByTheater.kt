package woowacourse.movie.model.theater

import woowacourse.movie.R
import woowacourse.movie.model.movie.Movie
import java.time.LocalDate
import java.time.LocalTime

data class MovieScreeningInfoByTheater(
    val movie: Movie,
    val screeningInfo: ScreeningInfo,
) {
    companion object {
        val values =
            listOf(
                MovieScreeningInfoByTheater(
                    movie =
                        Movie(
                            id = 1L,
                            title = "범죄도시 4",
                            poster = R.drawable.harry_potter_rock,
                            startDate = LocalDate.of(2025, 5, 1),
                            endDate = LocalDate.of(2025, 6, 1),
                            runningTime = 125,
                        ),
                    screeningInfo =
                        ScreeningInfo(
                            theater = Theater(name = "CGV 강남"),
                            screeningTimes =
                                listOf(
                                    LocalTime.of(14, 0),
                                    LocalTime.of(17, 30),
                                    LocalTime.of(20, 0),
                                ),
                        ),
                ),
                MovieScreeningInfoByTheater(
                    movie =
                        Movie(
                            id = 2L,
                            title = "듄: 파트 2",
                            poster = R.drawable.harry_potter_rock,
                            startDate = LocalDate.of(2025, 4, 20),
                            endDate = LocalDate.of(2025, 6, 10),
                            runningTime = 145,
                        ),
                    screeningInfo =
                        ScreeningInfo(
                            theater = Theater(name = "메가박스 신촌"),
                            screeningTimes =
                                listOf(
                                    LocalTime.of(15, 10),
                                    LocalTime.of(19, 0),
                                ),
                        ),
                ),
                MovieScreeningInfoByTheater(
                    movie =
                        Movie(
                            id = 3L,
                            title = "쿵푸팬더 4",
                            poster = R.drawable.harry_potter_rock,
                            startDate = LocalDate.of(2025, 4, 5),
                            endDate = LocalDate.of(2025, 5, 15),
                            runningTime = 94,
                        ),
                    screeningInfo =
                        ScreeningInfo(
                            theater = Theater(name = "롯데시네마 홍대"),
                            screeningTimes =
                                listOf(
                                    LocalTime.of(11, 45),
                                    LocalTime.of(14, 20),
                                    LocalTime.of(16, 50),
                                ),
                        ),
                ),
            )
    }
}
