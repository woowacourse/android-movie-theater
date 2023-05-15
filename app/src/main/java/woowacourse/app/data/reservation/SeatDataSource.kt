package woowacourse.app.data.reservation

interface SeatDataSource {
    fun insertSeat(
        reservationId: Long,
        rank: Int,
        row: Int,
        column: Int,
    ): SeatEntity

    fun getSeatEntities(reservationId: Long): List<SeatEntity>

    fun getSeatEntity(id: Long): SeatEntity?
}
