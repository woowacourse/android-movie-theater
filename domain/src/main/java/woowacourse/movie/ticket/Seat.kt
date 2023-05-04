package woowacourse.movie.ticket

data class Seat(
    val rank: SeatRank,
    val position: Position,
) {

    companion object {

        fun valueOf(position: Position): Seat {
            val rank = when (position.row) {
                in 0..1 -> SeatRank.B
                in 2..3 -> SeatRank.S
                else -> SeatRank.A
            }

            return Seat(rank, position)
        }
    }
}
