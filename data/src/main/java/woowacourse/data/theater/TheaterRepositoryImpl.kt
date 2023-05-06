package woowacourse.data.theater

import woowacourse.data.theater.TheaterMapper.toTheater
import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository

class TheaterRepositoryImpl(private val theaterDataSource: TheaterDataSource) : TheaterRepository {
    override fun getTheaters(): List<Theater> {
        return theaterDataSource.getTheaterEntities().map { it.toTheater() }
    }

    override fun getTheater(theaterId: Long): Theater? {
        return theaterDataSource.getTheaterEntity(theaterId)?.toTheater()
    }
}
