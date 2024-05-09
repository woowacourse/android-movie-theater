package woowacourse.movie.db.seats

import woowacourse.movie.model.seats.Grade.A
import woowacourse.movie.model.seats.Grade.B
import woowacourse.movie.model.seats.Grade.S
import woowacourse.movie.model.seats.TheaterSeat

object SeatsDatabase {
    val theaterSeats =
        listOf(
            TheaterSeat('A', 1, B),
            TheaterSeat('A', 2, B),
            TheaterSeat('A', 3, B),
            TheaterSeat('A', 4, B),
            TheaterSeat('B', 1, B),
            TheaterSeat('B', 2, B),
            TheaterSeat('B', 3, B),
            TheaterSeat('B', 4, B),
            TheaterSeat('C', 1, S),
            TheaterSeat('C', 2, S),
            TheaterSeat('C', 3, S),
            TheaterSeat('C', 4, S),
            TheaterSeat('D', 1, S),
            TheaterSeat('D', 2, S),
            TheaterSeat('D', 3, S),
            TheaterSeat('D', 4, S),
            TheaterSeat('E', 1, A),
            TheaterSeat('E', 2, A),
            TheaterSeat('E', 3, A),
            TheaterSeat('E', 4, A),
        )
}
