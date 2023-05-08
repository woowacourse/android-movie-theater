package woowacourse.app.data.theater

import woowacourse.domain.theater.SeatStructure

interface TheaterDataSource {
    fun getTheaterEntities(): List<TheaterEntity>
    fun getTheaterEntity(theaterId: Long): TheaterEntity?
    fun addTheaterEntity(
        theaterName: String,
        movieIds: List<Long>,
        seatStructure: SeatStructure,
    ): Long
}
