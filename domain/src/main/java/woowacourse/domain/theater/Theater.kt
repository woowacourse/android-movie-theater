package woowacourse.domain.theater

import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.SeatRank

data class Theater(
    val id: Long,
    val name: String,
    val screeningMovies: List<ScreeningMovie>,
    val seatStructure: SeatStructure,
) {
    private val rankMap: Map<SeatRank, List<IntRange>>
        get() = mapOf(
            SeatRank.S to seatStructure.sRankRange,
            SeatRank.A to seatStructure.aRankRange,
            SeatRank.B to seatStructure.bRankRange,
        )

    fun selectSeat(position: Position): Seat {
        return Seat(getSeatRank(position.row), position)
    }

    private fun getSeatRank(row: Int): SeatRank {
        rankMap.forEach {
            if (it.value.any { range -> row in range }) {
                return it.key
            }
        }
        throw IllegalArgumentException()
    }
}
