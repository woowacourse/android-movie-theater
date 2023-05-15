package woowacourse.app.data.reservation

data class SeatEntity(
    val id: Long,
    val reservationId: Long,
    val rank: Int,
    val row: Int,
    val column: Int,
)
