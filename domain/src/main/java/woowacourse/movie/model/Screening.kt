package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalTime

data class Screening(
    val id: Long,
    val movie: Movie,
    val theater: Theater,
    val schedules: List<Schedule>,
) {
    val startDate: LocalDate = schedules.first().date

    val endDate: LocalDate = schedules.last().date

    fun totalScreeningTimesNum(): Int = schedules.flatMap { it.times }.size

    companion object {
        val STUB_A: Screening =
            Screening(
                id = 0,
                movie = Movie.STUB,
                theater =
                    Theater.STUB_A,
                schedules =
                    listOf(
                        Schedule(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        Schedule(
                            LocalDate.of(2024, 3, 3),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        Schedule(
                            LocalDate.of(2024, 3, 4),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        Schedule(
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
                theater = Theater.STUB_B,
                schedules =
                    listOf(
                        Schedule(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        Schedule(
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
                theater = Theater.STUB_C,
                schedules =
                    listOf(
                        Schedule(
                            LocalDate.of(2024, 3, 1),
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(12, 0),
                            ),
                        ),
                        Schedule(
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
