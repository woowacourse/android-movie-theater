package woowacourse.movie.domain.model.seat

import java.io.Serializable

data class Seat(
    val x: Column,
    val y: Row,
) : Serializable {
    fun price() = SeatPolicy.get(y.value).price
}
