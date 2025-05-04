package woowacourse.movie.view.theater

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.toUiModel
import woowacourse.movie.view.model.MovieFixture.movies
import woowacourse.movie.view.model.MovieFixture.screenings
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUIModel
import java.time.LocalDate
import java.time.LocalDateTime

class TheaterPresenter(
    val view: TheaterContract.View,
    private val movie: MovieUiModel,
) : TheaterContract.Presenter {
    override fun fetchTheaters() {
        val theaterNames = getTheaterNames()
        val theaters =
            theaterNames.map { name ->
                val movies = getMovies(name)
                Theater(name, movies)
            }

        view.showTheaters(
            theaters.map { theater: Theater ->
                theater.toUiModel(
                    movie,
                    getTotalTimeSlotCount(theater, movie),
                )
            },
        )
    }

    override fun theaterSelected(theaterUIModel: TheaterUIModel) {
        if (theaterUIModel.timeSlotCount == 0) {
            view.showEmptySlotMessage()
        } else {
            view.navigateToReservation(theaterUIModel)
        }
    }

    private fun getTheaterNames(): List<String> = screenings.keys.toList()

    private fun getMovies(theaterName: String): List<Movie> {
        val movieNames = screenings[theaterName]?.keys ?: return emptyList()
        return movieNames.mapNotNull { movieName -> movies[movieName] }
    }

    private fun getScreenTimes(
        theaterName: String,
        movieName: String,
    ): List<Int> = getTimeSlot(theaterName)[movieName] ?: emptyList()

    private fun getTimeSlot(theaterName: String): Map<String, List<Int>> = screenings[theaterName] ?: emptyMap()

    private fun getTimeTable(
        now: LocalDateTime,
        selectedDate: LocalDate,
        screenTimes: List<Int>,
    ): List<Int> {
        if (now.toLocalDate() == selectedDate) {
            return screenTimes.timeTable(now.hour)
        }
        return screenTimes
    }

    private fun getTotalTimeSlotCount(
        theater: Theater,
        movie: MovieUiModel,
    ): Int {
        if (movie.name !in movies) {
            return 0
        }

        val endDate = theater.movies.find { it.title == movie.name }?.endDate ?: LocalDate.now()
        val now = LocalDateTime.now()
        var date = now.toLocalDate()
        var count = 0
        while (!date.isAfter(endDate)) {
            count += getTimeTable(now, date, getScreenTimes(theater.name, movie.name)).size
            date = date.plusDays(1)
        }
        return count
    }
}

private fun List<Int>.timeTable(nowHour: Int): List<Int> {
    forEachIndexed { index, time ->
        if (time > nowHour) {
            return slice(index..<size)
        }
    }
    return emptyList()
}
