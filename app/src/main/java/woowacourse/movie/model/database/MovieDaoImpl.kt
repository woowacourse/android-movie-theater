package woowacourse.movie.model.database

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.collections.forEach

class MovieDaoImpl(
    private val database: MovieDatabaseSchema,
) : MovieDao {
    override fun getTheaterNames(): List<String> = database.screenings.keys.toList()

    override fun getShowingMovies(now: LocalDateTime): List<Movie> {
        val today = now.toLocalDate()

        val result = mutableSetOf<Movie>()
        getTheaterNames().forEach { theaterName ->
            getMovies(theaterName).forEach { movie ->
                val screenTimes = getScreenTimes(theaterName, movie.title)
                if (today == movie.endDate) {
                    if (screenTimes.any { time -> time > now.hour }) {
                        result.add(movie)
                    }
                } else if (!today.isBefore(movie.startDate) && !today.isAfter(movie.endDate)) {
                    result.add(movie)
                }
            }
        }

        return result.toList()
    }

    override fun getTimeTable(
        now: LocalDateTime,
        selectedDate: LocalDate,
        screenTimes: List<Int>,
    ): List<Int> {
        if (now.toLocalDate() == selectedDate) {
            return screenTimes.timeTable(now.hour)
        }
        return screenTimes
    }

    override fun getTotalTimeSlotCount(
        theater: Theater,
        movie: Movie,
        endDate: LocalDate,
        now: LocalDateTime,
    ): Int {
        var date = now.toLocalDate()
        var count = 0
        while (!date.isAfter(endDate)) {
            count += getTimeTable(now, date, getScreenTimes(theater.name, movie.title)).size
            date = date.plusDays(1)
        }
        return count
    }

    override fun getMovies(theaterName: String): List<Movie> {
        val movieNames = database.screenings[theaterName]?.keys ?: return emptyList()
        return movieNames.mapNotNull { movieName -> database.movies[movieName] }
    }

    override fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> = getTimeSlot(theaterName)[movieName] ?: emptyList()

    private fun getTimeSlot(theaterName: String): Map<String, List<Int>> = database.screenings[theaterName] ?: emptyMap()
}

private fun List<Int>.timeTable(nowHour: Int): List<Int> {
    forEachIndexed { index, time ->
        if (time > nowHour) {
            return slice(index..<size)
        }
    }
    return emptyList()
}
