package woowacourse.app.data.theater

import woowacourse.domain.theater.Theater

object TheaterMapper {
    fun TheaterEntity.toTheater(): Theater {
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
