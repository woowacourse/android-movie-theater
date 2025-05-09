package woowacourse.movie.domain.model

import java.time.LocalDateTime
import java.time.ZoneId

data class BookingInfo(
    val id: Long? = null,
    val movie: Movie,
    val theaterName: String,
    private var date: MovieDate = movie.startDate,
    private var time: MovieTime = MovieTime(),
    private val seats: MovieSeats = MovieSeats(),
    private val ticketCount: TicketCount = TicketCount(),
) {
    val selectedDate: MovieDate get() = date
    val selectedTime: MovieTime get() = time
    val selectedSeats: Set<MovieSeat> get() = seats.value
    val totalPrice: TicketPrice get() = seats.totalPrice
    val currentTicketCount: Int get() = ticketCount.value
    val isSeatAllSelected: Boolean get() = selectedSeats.size == currentTicketCount

    fun updateDate(date: MovieDate) {
        this.date = date
    }

    fun updateMovieTime(movieTime: MovieTime) {
        time = movieTime
    }

    fun increaseTicketCount(count: Int = 1) {
        ticketCount.increase(count)
    }

    fun decreaseTicketCount(count: Int = 1) {
        ticketCount.decrease(count)
    }

    fun updateSeat(seat: MovieSeat): SeatSelectionResult =
        if (seats.value.contains(seat)) {
            removeSeat(seat)
            SeatSelectionResult.Success(seat.copy(isSelected = false))
        } else {
            addSeat(seat.copy(isSelected = true))
        }

    fun addSeat(seat: MovieSeat): SeatSelectionResult =
        if (ticketCount.value > selectedSeats.size) {
            seats.add(seat)
            SeatSelectionResult.Success(seat)
        } else {
            SeatSelectionResult.ExceedCountFailure
        }

    fun getNotificationDelay(): Long {
        val dateTime = LocalDateTime.of(selectedDate.value, selectedTime.value)
        val triggerDateTime = dateTime.minusMinutes(BOOKING_NOTIFICATION_TIME_MINUTES)

        return triggerDateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    private fun removeSeat(seat: MovieSeat) {
        seats.remove(seat)
    }

    companion object {
        private const val BOOKING_NOTIFICATION_TIME_MINUTES = 30L
    }
}
