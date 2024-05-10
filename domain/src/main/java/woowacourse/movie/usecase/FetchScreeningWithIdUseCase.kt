package woowacourse.movie.usecase

import woowacourse.movie.model.Screening
import woowacourse.movie.repository.EverythingRepository
import woowacourse.movie.repository.ScreeningRefRepository
import woowacourse.movie.repository.TheaterRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

class FetchScreeningWithIdUseCase(
    private val everythingRepository: EverythingRepository,
    private val theaterRepository: TheaterRepository,
    private val screeningRefRepository: ScreeningRefRepository,
) {
    operator fun invoke(screeningId: Long): Result<Screening> {
        return runCatching {
            val screeningRef =
                screeningRefRepository.screeningRefById(screeningId)
                    ?: throw NoSuchElementException(
                        NO_SCREENING_REF,
                    )
            val movie =
                everythingRepository.movieById(screeningRef.movieId - 1)
                    ?: throw NoSuchElementException(
                        NO_MOVIE,
                    )
            val theater =
                theaterRepository.theaterById(screeningRef.theaterId)
                    ?: throw NoSuchElementException(
                        NO_THEATER,
                    )

            val localDateTime =
                LocalDateTime.ofEpochSecond(screeningRef.screeningTimeStamp, 0, ZoneOffset.UTC)
            Screening(screeningId, movie, theater, localDateTime)
        }
    }

    companion object {
        private const val NO_SCREENING_REF = "해당하는 id의 screening ref가 없습니다"
        private const val NO_MOVIE = "해당하는 id의 movie가 없습니다"
        private const val NO_THEATER = "해당하는 id의 theater가 없습니다"
    }
}
