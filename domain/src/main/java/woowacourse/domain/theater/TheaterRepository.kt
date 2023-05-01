package woowacourse.domain.theater

import woowacourse.data.theater.TheaterDatabase
import woowacourse.data.theater.TheaterEntity

object TheaterRepository {
    fun getTheaters(): List<Theater> {
        return TheaterDatabase.theaters.map { it.toTheater() }
    }

    fun getTheater(theaterId: Long): Theater {
        return TheaterDatabase.selectTheater(theaterId).toTheater()
    }

    private fun TheaterEntity.toTheater(): Theater {
        return Theater(
            id = this.id,
            rowSize = this.rowSize,
            columnSize = this.columnSize,
            sRankRange = this.sRankRange,
            aRankRange = this.aRankRange,
            bRankRange = this.bRankRange,
        )
    }
}
