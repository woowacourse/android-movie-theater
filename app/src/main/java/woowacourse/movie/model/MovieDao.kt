package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalDateTime

class MovieDao(
    val movieDatabase: MovieDatabaseSchema = MovieDatabase(),
) {
    fun getTheaterNames(): List<String> = movieDatabase.screenings.keys.toList()

    fun getShowingMovies(now: LocalDateTime = LocalDateTime.now()): List<Movie> {
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

    fun getTimeTable(
        now: LocalDateTime = LocalDateTime.now(),
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
        now: LocalDateTime = LocalDateTime.now(),
    ): Int {
        var date = now.toLocalDate()
        var count = 0
        while (!date.isAfter(endDate)) {
            count += getTimeTable(now, date, getScreenTimes(theater.name, movie.title)).size
            date = date.plusDays(1)
        }
        return count
    }

    fun getMovies(theaterName: String): List<Movie> {
        val movieNames = movieDatabase.screenings[theaterName]?.keys ?: return emptyList()
        return movieNames.mapNotNull { movieName -> movieDatabase.movies[movieName] }
    }

    fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> = getTimeSlot(theaterName)[movieName] ?: emptyList()

    private fun getTimeSlot(theaterName: String): Map<String, List<Int>> = movieDatabase.screenings[theaterName] ?: emptyMap()
}

private fun List<Int>.timeTable(nowHour: Int): List<Int> {
    forEachIndexed { index, time ->
        if (time > nowHour) {
            return slice(index..<size)
        }
    }
    return emptyList()
}
