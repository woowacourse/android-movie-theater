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
                val movie =
                    movieRepository.movieById(it.movieId) ?: throw NoSuchElementException(
                        NO_MOVIE,
                    )
                val theater =
                    theaterRepository.theaterById(it.theaterId) ?: throw NoSuchElementException(
                        NO_THEATER,
                    )
                val localDateTime =
                    LocalDateTime.ofEpochSecond(it.screeningTimeStamp, 0, ZoneOffset.UTC)
                Screening(it.id, movie, theater, localDateTime)
            }
        }
    }

    companion object {
        private const val NO_MOVIE = "해당하는 id의 movie가 없습니다"
        private const val NO_THEATER = "해당하는 id의 theater가 없습니다"
    }
}
