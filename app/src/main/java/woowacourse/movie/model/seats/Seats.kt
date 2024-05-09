package woowacourse.movie.model.seats

import java.io.Serializable

class Seats : Serializable {
    var theaterSeats = mutableListOf<TheaterSeat>()
        private set

    var seatsIndex = mutableListOf<Int>()
        private set

    fun restoreSeats(recordOfSeats: Seats): Seats {
        theaterSeats = recordOfSeats.theaterSeats
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
