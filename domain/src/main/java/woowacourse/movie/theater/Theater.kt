package woowacourse.movie.theater

import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat

data class Theater(
    val id: Long,
    val rowSize: Int,
    val columnSize: Int,
) {

    fun selectSeat(position: Position): Seat {
        return Seat.valueOf(position)
    }
}
