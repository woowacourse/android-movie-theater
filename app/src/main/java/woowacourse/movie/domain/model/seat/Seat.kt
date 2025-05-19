package woowacourse.movie.domain.model.seat

import java.io.Serializable

data class Seat(
    val col: Col,
    val row: Row,
) : Serializable {
    fun price() = SeatPolicy.get(row.value).price
}
