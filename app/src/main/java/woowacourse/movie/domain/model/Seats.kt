package woowacourse.movie.domain.model

import java.io.Serializable

class Seats(
    private val _reservingSeats: MutableSet<Seat> = mutableSetOf(),
) : Serializable {
    val reservingSeats get() = _reservingSeats.toSet()

    fun isReservedSeat(seat: Seat): Boolean {
        return reservingSeats.contains(seat)
    }

    fun reserve(seat: Seat) {
        _reservingSeats.add(seat)
    }

    fun cancelReserve(seat: Seat) {
        _reservingSeats.remove(seat)
    }

    fun isSeatSelectionComplete(headcount: Headcount): Boolean {
        return reservingSeats.size == headcount.count
    }

    fun totalPrice(): Int {
        return reservingSeats.sumOf { seat -> seat.price() }
    }
}
