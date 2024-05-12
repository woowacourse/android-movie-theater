package woowacourse.movie.usecase

import woowacourse.movie.model.Theater
import woowacourse.movie.repository.ScreeningRefRepository
import woowacourse.movie.repository.TheaterRepository

class FetchTheatersWithMovieIdUseCase(
    private val screeningRefRepository: ScreeningRefRepository,
    private val theaterRepository: TheaterRepository,
) {
    operator fun invoke(movieId: Long): Result<List<Theater>> {
        val screeningRefs = screeningRefRepository.screeningRefsByMovieId(movieId)
        val theaterIds = screeningRefs.map { it.theaterId }
        return runCatching { theaterRepository.theatersById(theaterIds) }
    }
}
