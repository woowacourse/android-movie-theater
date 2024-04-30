package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.SeatRank

object DummySeatBoard {
    private val seatBoard =
        SeatBoard(
            0,
            4,
            5,
            listOf(
                Seat("A", 0, SeatRank.B),
                Seat("A", 1, SeatRank.B),
                Seat("A", 2, SeatRank.B),
                Seat("A", 3, SeatRank.B),
                Seat("B", 0, SeatRank.B),
                Seat("B", 1, SeatRank.B),
                Seat("B", 2, SeatRank.B),
                Seat("B", 3, SeatRank.B),
                Seat("C", 0, SeatRank.S),
                Seat("C", 1, SeatRank.S),
                Seat("C", 2, SeatRank.S),
                Seat("C", 3, SeatRank.S),
                Seat("D", 0, SeatRank.S),
                Seat("D", 1, SeatRank.S),
                Seat("D", 2, SeatRank.S),
                Seat("D", 3, SeatRank.S),
                Seat("E", 0, SeatRank.A),
                Seat("E", 1, SeatRank.A),
                Seat("E", 2, SeatRank.A),
                Seat("E", 3, SeatRank.A),
            ),
        )

    val seatBoards = listOf(seatBoard, seatBoard.copy(id = 1), seatBoard.copy(id = 2))
}
