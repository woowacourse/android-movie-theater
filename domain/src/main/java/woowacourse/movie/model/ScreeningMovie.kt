package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalTime

data class ScreeningMovie(
    val id: Long,
    val movie: Movie,
    val theater: MovieTheater,
    val screenDateTimes: List<ScreenDateTime>,
) {
    val startDate: LocalDate = screenDateTimes.first().date

    val endDate: LocalDate = screenDateTimes.last().date

    fun screeningTimeOfDate(screeningDate: LocalDate): ScreenDateTime =
        screenDateTimes.firstOrNull { it.date.isEqual(screeningDate) }
            ?: error("해당 날짜에는 상영하지 않습니다.")

    fun totalScreeningTimesNum(): Int = screenDateTimes.flatMap { it.times }.size

    companion object {
        val STUB_A: ScreeningMovie =
            ScreeningMovie(
                id = 0,
                movie = Movie.STUB,
                theater =
                    MovieTheater.STUB_A,
                screenDateTimes =
                    listOf(
                        ScreenDateTime(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreenDateTime(
                            LocalDate.of(2024, 3, 3),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                    ),
            )

        val STUB_B: ScreeningMovie =
            ScreeningMovie(
                id = 1,
                movie = Movie.STUB,
                theater = MovieTheater.STUB_B,
                screenDateTimes =
                    listOf(
                        ScreenDateTime(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreenDateTime(
                            LocalDate.of(2024, 3, 3),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                    ),
            )

        val STUB_C: ScreeningMovie =
            ScreeningMovie(
                id = 2,
                movie = Movie.STUB,
                theater = MovieTheater.STUB_C,
                screenDateTimes =
                    listOf(
                        ScreenDateTime(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreenDateTime(
                            LocalDate.of(2024, 3, 3),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                    ),
            )
    }
}
