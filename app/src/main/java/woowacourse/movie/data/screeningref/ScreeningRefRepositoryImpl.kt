package woowacourse.movie.data.screeningref

import woowacourse.movie.model.ScreeningRef
import woowacourse.movie.repository.ScreeningRefRepository

class ScreeningRefRepositoryImpl(private val screeningRefDao: ScreeningRefDao) :
    ScreeningRefRepository {
    override fun screeningRefById(id: Long): ScreeningRef? = screeningRefDao.findById(id)?.toScreeningRef()

    override fun screeningRefsByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<ScreeningRef> = screeningRefDao.findByMovieIdAndTheaterId(movieId, theaterId).map { it.toScreeningRef() }
}
