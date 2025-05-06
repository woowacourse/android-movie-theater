package woowacourse.movie.domain.model.seat

import java.io.Serializable

class Seat private constructor(val seatPosition: SeatPosition) : Serializable {
    val grade: SeatGrade = SeatGrade.from(seatPosition.y)

    companion object {
        fun of(x: Int, y: Int): Seat = Seat(SeatPosition(x, y))
    }
}
