package woowacourse.movie.usecase

import woowacourse.movie.model.Screening
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.repository.ScreeningRefRepository
import woowacourse.movie.repository.TheaterRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

class FetchScreeningsWithMovieIdAndTheaterIdUseCase(
    private val movieRepository: MovieRepository,
    private val theaterRepository: TheaterRepository,
    private val screeningRefRepository: ScreeningRefRepository,
) {
    operator fun invoke(
        movieId: Long,
        theaterId: Long,
    ): Result<List<Screening>> {
        return runCatching {
            val screeningRefs =
                screeningRefRepository.screeningRefsByMovieIdAndTheaterId(movieId, theaterId)
            screeningRefs.map {
                val movie = movieRepository.movieById(it.movieId) ?: throw error("")
                val theater = theaterRepository.theaterById(it.theaterId) ?: throw error("")
                val localDateTime =
                    LocalDateTime.ofEpochSecond(it.screeningTimeStamp, 0, ZoneOffset.UTC)
                Screening(it.id, movie, theater, localDateTime)
            }
        }
    }
}
