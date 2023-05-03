package woowacourse.data.theater

import woowacourse.data.theater.TheaterMapper.toTheater
import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository

class TheaterRepositoryImpl : TheaterRepository {
    override fun getTheaters(): List<Theater> {
        return TheaterDatabase.theaters.map { it.toTheater() }
    }

    override fun getTheater(theaterId: Long): Theater? {
        return TheaterDatabase.selectTheater(theaterId)?.toTheater()
    }
}
