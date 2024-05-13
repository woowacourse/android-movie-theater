package woowacourse.movie.model.data.theater

import woowacourse.movie.model.Theater
import woowacourse.movie.repository.TheaterRepository

class TheaterRepositoryImpl(private val theaterDao: TheaterDao) : TheaterRepository {
    override fun theaterById(theaterId: Long): Theater? = theaterDao.findById(theaterId)?.toTheater()

    override fun theatersById(theaterIds: List<Long>): List<Theater> = theaterDao.findByIds(theaterIds).map { it.toTheater() }
}
