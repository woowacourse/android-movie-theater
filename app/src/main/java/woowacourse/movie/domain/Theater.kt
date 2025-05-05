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
            theaters: List<Theater>,
        ): List<Showings> {
            return theaters.map { theater ->
                val totalShowings =
                    theater.schedule
                        .firstOrNull { it.movieTitle == targetMovieTitle }

                val todayFilter =
                    ScheduleTime(
                        totalShowings?.scheduleTime?.times ?: emptyList(),
                    )

                Showings(theater.name, todayFilter)
            }
        }
    }
}

val theatersDummy =
    listOf(
        Theater(
            "선릉 극장",
            listOf(
                Schedule(
                    "어거스트 러쉬",
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
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
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
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
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
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
                            LocalTime.of(23, 0),
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
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
                            LocalTime.of(23, 0),
                        ),
                    ),
                ),
            ),
        ),
        Theater(
            "극장이름이 너무너무 길어요 극장",
            listOf(
                Schedule(
                    "스즈메의 문단속",
                    ScheduleTime(
                        listOf(
                            LocalTime.of(13, 0),
                            LocalTime.of(15, 0),
                            LocalTime.of(17, 0),
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
                            LocalTime.of(23, 0),
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
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
                            LocalTime.of(23, 0),
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
                            LocalTime.of(19, 0),
                            LocalTime.of(21, 0),
                            LocalTime.of(23, 0),
                        ),
                    ),
                ),
            ),
        ),
    )
