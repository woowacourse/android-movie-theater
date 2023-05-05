package woowacourse.movie.theater

import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat
import java.time.LocalTime

data class Theater(
    val id: Long,
    val name: String,
    val rowSize: Int,
    val columnSize: Int,
    val screeningTimes: List<LocalTime>
) {

    fun selectSeat(position: Position): Seat {
        return Seat.valueOf(position)
    }
}
