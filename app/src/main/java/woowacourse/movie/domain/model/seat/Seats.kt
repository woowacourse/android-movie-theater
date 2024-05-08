package woowacourse.movie.domain.model.seat

data class Seats(
    val seats: List<SelectedSeat>,
    private val maxSelectableSeats: Int,
) {
    fun toggleSeatSelection(index: Int): SeatSelectionResult {
        val seatUIModel = seats[index]
        when {
            seatUIModel.isSelected -> {
                seatUIModel.isSelected = false
                return SeatSelectionResult.Success
            }
            selectedCount() < maxSelectableSeats -> {
                seatUIModel.isSelected = true
                if (selectedCount() == maxSelectableSeats) {
                    return SeatSelectionResult.MaxCapacityReached
                }
                return SeatSelectionResult.Success
            }
            selectedCount() == maxSelectableSeats -> {
                return SeatSelectionResult.AlreadyMaxCapacityReached
            }
            else -> return SeatSelectionResult.Failure
        }
    }

    private fun selectedCount(): Int = seats.count { it.isSelected }

    private fun selectedSeats(): List<Seat> = seats.filter { it.isSelected }.map { it.seat }

    override fun toString(): String {
        return selectedSeats().joinToString(", ")
    }

    fun selectedSeatIndices(): List<Int> =
        seats.mapIndexedNotNull { index, seatUIModel ->
            if (seatUIModel.isSelected) {
                index
            } else {
                null
            }
        }

    fun totalPrice(): Int = selectedSeats().sumOf { it.seatGrade.price }
}
