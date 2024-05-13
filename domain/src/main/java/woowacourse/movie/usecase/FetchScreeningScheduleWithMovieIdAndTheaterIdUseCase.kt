package woowacourse.movie.usecase

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Schedule
import woowacourse.movie.model.ScreeningSchedule
import woowacourse.movie.model.Theater

class FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(
    private val fetchScreeningsWithMovieIdAndTheaterIdUseCase: FetchScreeningsWithMovieIdAndTheaterIdUseCase,
) {
    operator fun invoke(
        movieId: Long,
        theaterId: Long,
    ) = runCatching {
        lateinit var movie: Movie
        lateinit var theater: Theater
        fetchScreeningsWithMovieIdAndTheaterIdUseCase(
            movieId,
            theaterId,
        ).map { screenings ->
            movie = screenings.first().movie
            theater = screenings.first().theater
            screenings.map { screening ->
                screening.localDateTime.toLocalDate() to screening.localDateTime.toLocalTime()
            }.groupBy(
                { it.first },
                { it.second },
            ).map { (date, times) ->
                Schedule(date, times)
            }
        }.map { ScreeningSchedule(movie, theater, it) }.getOrThrow()
    }
}
