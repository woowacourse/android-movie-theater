package woowacourse.movie.model

data class SelectedSeats(
    private val theater: MovieTheater,
    private val headCount: HeadCount,
    val selectedSeats: List<Seat> = emptyList(),
) {
    val totalPrice = Price(selectedSeats.sumOf { it.price })

    init {
        require(selectedSeats.distinct().size == selectedSeats.size) {
            "중복된 좌석이 들어올 수 없습니다."
        }

        require(selectedSeats.all { it.state == SeatState.SELECTED }) {
            "현재 선택된 좌석만 들어올 수 있습니다."
        }
    }

    fun select(
        row: Int,
        col: Int,
    ): SelectResult {
        val selectedSeat = theater.seat(row, col).changeState()

        return if (selectedSeats.none { it.row == row && it.col == col }) {
            val updatedSelectedSeats = selectedSeats + theater.seat(row, col).changeState()
            selectResult(selectedSeat, updatedSelectedSeats)
        } else {
            val updatedSelectedSeats = selectedSeats - theater.seat(row, col).changeState()
            selectResult(selectedSeat, updatedSelectedSeats)
        }
    }

    private fun selectResult(
        seat: Seat,
        selectedSeats: List<Seat>,
    ): SelectResult {
        val updatedSelectedSeats = copy(selectedSeats = selectedSeats)

        return when {
            theater.alreadySelected().contains(seat.changeState()) ->
                SelectResult.AlreadyReserve(
                    updatedSelectedSeats,
                )

            selectedSeats.size > headCount.count -> SelectResult.Exceed(updatedSelectedSeats)
            selectedSeats.size < headCount.count ->
                SelectResult.LessSelect(
                    updatedSelectedSeats,
                )

            selectedSeats.size == headCount.count ->
                SelectResult.Success(
                    updatedSelectedSeats,
                )

            else -> throw IllegalArgumentException()
        }
    }
}
