package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalDateTime

interface MovieDao {
    fun getTheaterNames(): List<String>

    fun getShowingMovies(now: LocalDateTime = LocalDateTime.now()): List<Movie>

    fun getTimeTable(
        now: LocalDateTime = LocalDateTime.now(),
        selectedDate: LocalDate,
        screenTimes: List<Int>,
    ): List<Int>

    fun getTotalTimeSlotCount(
        theater: Theater,
        movie: Movie,
        endDate: LocalDate,
        now: LocalDateTime = LocalDateTime.now(),
    ): Int

    fun getMovies(theaterName: String): List<Movie>

    fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int>
}
