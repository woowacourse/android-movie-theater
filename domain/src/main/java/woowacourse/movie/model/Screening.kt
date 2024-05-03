package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalTime

data class Screening(
    val id: Long,
    val movie: Movie,
    val theater: MovieTheater,
    val screeningDateTimes: List<ScreeningDateTime>,
) {
    val startDate: LocalDate = screeningDateTimes.first().date

    val endDate: LocalDate = screeningDateTimes.last().date

    fun screeningTimeOfDate(screeningDate: LocalDate): ScreeningDateTime =
        screeningDateTimes.firstOrNull { it.date.isEqual(screeningDate) }
            ?: error("해당 날짜에는 상영하지 않습니다.")

    fun totalScreeningTimesNum(): Int = screeningDateTimes.flatMap { it.times }.size

    companion object {
        val STUB_A: Screening =
            Screening(
                id = 0,
                movie = Movie.STUB,
                theater =
                    MovieTheater.STUB_A,
                screeningDateTimes =
                    listOf(
                        ScreeningDateTime(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreeningDateTime(
                            LocalDate.of(2024, 3, 3),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreeningDateTime(
                            LocalDate.of(2024, 3, 4),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreeningDateTime(
                            LocalDate.of(2024, 3, 5),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                    ),
            )

        val STUB_B: Screening =
            Screening(
                id = 1,
                movie = Movie.STUB,
                theater = MovieTheater.STUB_B,
                screeningDateTimes =
                    listOf(
                        ScreeningDateTime(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreeningDateTime(
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

        val STUB_C: Screening =
            Screening(
                id = 2,
                movie = Movie.STUB,
                theater = MovieTheater.STUB_C,
                screeningDateTimes =
                    listOf(
                        ScreeningDateTime(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        ScreeningDateTime(
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
