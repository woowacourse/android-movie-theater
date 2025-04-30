package woowacourse.movie.domain

import java.io.Serializable
import java.time.LocalTime

data class Theater(
    val name: String,
    val schedule: List<Schedule>,
) : Serializable {
    companion object {
        fun findTheatersShowingMovie(
            targetMovieTitle: String,
            currentTime: LocalTime,
        ): List<Showings> {
            return theatersDummy.map { theater ->
                val totalShowings =
                    theater.schedule
                        .firstOrNull { it.movieTitle == targetMovieTitle }

                val todayFilter =
                    ScheduleTime(
                        totalShowings?.scheduleTime?.afterCurrentTimeSchedule(currentTime)
                            ?: emptyList(),
                    )

                Showings(theater.name, todayFilter)
            }
        }

        private val theatersDummy =
            listOf(
                Theater(
                    "선릉 극장",
                    listOf(
                        Schedule(
                            "스타워즈",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(9, 0),
                                    LocalTime.of(11, 0),
                                    LocalTime.of(15, 0),
                                ),
                            ),
                        ),
                        Schedule(
                            "해리포터",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                    ),
                ),
                Theater(
                    "잠실 극장",
                    listOf(
                        Schedule(
                            "스즈메의 문단속",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                        Schedule(
                            "해리포터",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                        Schedule(
                            "범죄도시",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                    ),
                ),
                Theater(
                    "강남 극장",
                    listOf(
                        Schedule(
                            "스즈메의 문단속",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                        Schedule(
                            "해리포터",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                        Schedule(
                            "범죄도시",
                            ScheduleTime(
                                listOf(
                                    LocalTime.of(13, 0),
                                    LocalTime.of(15, 0),
                                    LocalTime.of(17, 0),
                                ),
                            ),
                        ),
                    ),
                ),
            )
    }
}
