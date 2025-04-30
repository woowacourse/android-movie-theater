package woowacourse.movie.model

import woowacourse.movie.model.MovieDatabase.movies
import woowacourse.movie.model.MovieDatabase.screenings
import java.time.LocalDate
import java.time.LocalDateTime

class MovieDao {
    fun getTimeTable(
        now: LocalDateTime,
        selectedDate: LocalDate,
        screenTimes: List<Int>,
    ): List<Int> {
        if (now.toLocalDate() == selectedDate) {
            return screenTimes.timeTable(now.hour)
        }
        return screenTimes
    }

    fun getTotalTimeSlotCount(
        theater: Theater,
        movie: Movie,
        endDate: LocalDate,
    ): Int {
        val now = LocalDateTime.now()
        var date = now.toLocalDate()
        var count = 0
        while (!date.isAfter(endDate)) {
            count += getTimeTable(now, date, getScreenTimes(theater.name, movie.title)).size
            date = date.plusDays(1)
        }
        return count
    }

    fun getMovies(theaterName: String): List<Movie> {
        val movieNames = screenings[theaterName]?.keys ?: return emptyList()
        return movieNames.mapNotNull { movieName -> movies[movieName] }
    }

    fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> = getTimeSlot(theaterName)[movieName] ?: emptyList()

    private fun getTimeSlot(theaterName: String): Map<String, List<Int>> = screenings[theaterName] ?: emptyMap()
}

private fun List<Int>.timeTable(nowHour: Int): List<Int> {
    forEachIndexed { index, time ->
        if (time > nowHour) {
            return slice(index..<size)
        }
    }
    return emptyList()
}
