package woowacourse.movie.model.seats

import java.io.Serializable

class SeatSelection : Serializable {
    var theaterSeats = mutableListOf<TheaterSeat>()
        private set

    var seatsIndex = mutableListOf<Int>()
        private set

    fun restoreSeats(recordOfSeatSelection: SeatSelection): SeatSelection {
        theaterSeats = recordOfSeatSelection.theaterSeats
        return this
    }

    fun restoreSeatsIndex(seats: List<Int>) {
        seatsIndex = seats.toMutableList()
    }

    fun manageSelectedIndex(
        isSelected: Boolean,
        index: Int,
    ) {
        if (isSelected) seatsIndex.add(index) else seatsIndex.remove(index)
    }

    fun manageSelected(
        isSelected: Boolean,
        theaterSeat: TheaterSeat,
    ) {
        if (isSelected) theaterSeats.add(theaterSeat) else theaterSeats.remove(theaterSeat)
    }

    fun calculateAmount(): Int {
        return theaterSeats.sumOf { it.grade.price }
    }
}
