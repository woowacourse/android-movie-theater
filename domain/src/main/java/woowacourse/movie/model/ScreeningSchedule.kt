package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ScreeningSchedule(
    val movie: Movie,
    val theater: Theater,
    val schedules: List<Schedule>,
) {
    val startDate: LocalDate = schedules.first().date

    val endDate: LocalDate = schedules.last().date

    fun totalScreeningTimesNum(): Int = schedules.flatMap { it.times }.size

    fun getDateTimeAt(
        dateIndex: Int,
        timeIndex: Int,
    ): LocalDateTime {
        val date = schedules[dateIndex].date
        val time = schedules[dateIndex].times[timeIndex]
        return LocalDateTime.of(date, time)
    }

    companion object {
        val STUB_A: ScreeningSchedule =
            ScreeningSchedule(
                movie = Movie.STUB_A,
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
                            LocalDate.of(2024, 3, 2),
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

        val STUB_B: ScreeningSchedule =
            ScreeningSchedule(
                movie = Movie.STUB_A,
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
                            LocalDate.of(2024, 3, 2),
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

        val STUB_C: ScreeningSchedule =
            ScreeningSchedule(
                movie = Movie.STUB_A,
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
                            LocalDate.of(2024, 3, 2),
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

        val STUB_D: ScreeningSchedule =
            ScreeningSchedule(
                movie = Movie.STUB_B,
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
                            LocalDate.of(2024, 3, 2),
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
        val STUB_E: ScreeningSchedule =
            ScreeningSchedule(
                movie = Movie.STUB_C,
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
                            LocalDate.of(2024, 3, 2),
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
