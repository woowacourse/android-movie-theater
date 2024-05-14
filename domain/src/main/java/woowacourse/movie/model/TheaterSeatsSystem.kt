package woowacourse.movie.model

class TheaterSeatsSystem(
    private val rowRate: Map<SeatRate, List<Int>>,
    private val colLength: Int,
) {
    fun seats(): List<Seat> =
        rowRate.flatMap { (seatRate, rateRows) ->
            rateRows.flatMap { row ->
                (0 until colLength).map { col ->
                    Seat(seatRate, row - 1, col)
                }
            }
        }

    fun rateOfSeat(row: Int): SeatRate {
        val containRow = row + 1
        return rowRate.entries.firstOrNull { it.value.contains(containRow) }?.key
            ?: error("$row 해당 행에 헤당하는 등급이 없습니다.")
    }
}
