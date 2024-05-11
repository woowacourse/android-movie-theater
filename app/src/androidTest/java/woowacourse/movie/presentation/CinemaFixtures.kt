package woowacourse.movie.presentation

import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime


fun cinema(
    title: String = "차람과 하디의 진지한 여행기 1",
    year: Int = 2024,
    month: Int = 2,
    day: Int = 25,
    runningTime: Int = 230,
    synopsis: String = "wow!",
    times: List<LocalTime> = listOf(
        LocalTime.of(10, 0),
        LocalTime.of(14, 0),
        LocalTime.of(18, 0),
    ),
): Cinema {
    return Cinema(
        "CGV",
        Theater(
            MovieInfo(
                Title(title),
                MovieDate(LocalDate.of(year, month, day)),
                RunningTime(runningTime),
                Synopsis(synopsis),
            ),
            times = times,
            seats = mapOf(),
        ),
    )
}

fun movieInfo(
    title: String = "차람과 하디의 진지한 여행기 1",
    year: Int = 2024,
    month: Int = 2,
    day: Int = 25,
    runningTime: Int = 230,
    synopsis: String = "wow!",
): MovieInfo {
    return MovieInfo(
        Title(title),
        MovieDate(LocalDate.of(year, month, day)),
        RunningTime(runningTime),
        Synopsis(synopsis),
    )
}

fun seat(
    row: Char = 'A',
    number: Int = 1,
    grade: String = "5",
    chosen: Boolean = false,
): Seat = Seat(row, number, grade, chosen)

fun movieDate(year: Int, month: Int, day: Int): MovieDate =
    MovieDate(LocalDate.of(year, month, day))

fun runningTime(minutes: Int): RunningTime = RunningTime(minutes)

fun synopsis(content: String): Synopsis = Synopsis(content)