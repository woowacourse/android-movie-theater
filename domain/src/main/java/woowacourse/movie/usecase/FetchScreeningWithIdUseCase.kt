package woowacourse.movie.usecase

import woowacourse.movie.model.Screening
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.repository.ScreeningRefRepository
import woowacourse.movie.repository.TheaterRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

class FetchScreeningWithIdUseCase(
    private val movieRepository: MovieRepository,
    private val theaterRepository: TheaterRepository,
    private val screeningRefRepository: ScreeningRefRepository,
) {
    operator fun invoke(screeningId: Long): Result<Screening> {
        return runCatching {
            val screeningRef = screeningRefRepository.screeningRefById(screeningId) ?: throw error("no screeningRef")
            val movie = movieRepository.movieById(screeningRef.movieId) ?: throw error("no movie")
            val theater = theaterRepository.theaterById(screeningRef.theaterId) ?: throw error("no theater")
            val localDateTime = LocalDateTime.ofEpochSecond(screeningRef.screeningTimeStamp, 0, ZoneOffset.UTC)
            Screening(screeningId, movie, theater, localDateTime)
        }
    }
}
