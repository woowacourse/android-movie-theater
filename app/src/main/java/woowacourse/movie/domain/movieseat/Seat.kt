package woowacourse.movie.domain.movieseat

import java.io.Serializable

data class Seat(
    val position: Position,
) : Serializable {
    override fun toString(): String = "${position.row},${position.column}"

    fun seatPrice() = SeatRank.get(position.row).price
}
