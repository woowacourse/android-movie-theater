package woowacourse.movie.domain.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.BookingStatus

@Parcelize
data class BookingSeats(
    val value: Int,
    private val _seats: MutableList<Seat> = mutableListOf(),
) : Parcelable {
    val seats: List<Seat>
        get() = _seats

    init {
        require(value >= MINIMUM_NUMBER_OF_PEOPLE) { BookingSeatsResult.PeopleOverOne }
    }

    fun add(seat: Seat) {
        _seats.add(seat)
    }

    fun remove(seat: Seat) {
        _seats.remove(seat)
    }

    fun calculateTicketPrices(): Int {
        return _seats.sumOf { seat -> seat.price() }
    }

    fun isSelectedAll(): Boolean = this.value == this._seats.size

    companion object {
        private const val MINIMUM_NUMBER_OF_PEOPLE = 1
    }
}

sealed class BookingSeatsResult {
    data class Success(val bookingSeats: BookingSeats) : BookingSeatsResult()
    object PeopleOverOne : BookingSeatsResult()
}
