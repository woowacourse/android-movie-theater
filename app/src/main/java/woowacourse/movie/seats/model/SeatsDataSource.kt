package woowacourse.movie.seats.model

object SeatsDataSource {
    var date: String = ""
    var time: String = ""
    var seatTotalPrice = 0
    val selectedSeats
        get() = Seat.seats.filter { it.selected }
    var movieId: Long = -1
    var ticketCount: Int = -1
    var seat: Seat = Seat.of(1, 1)
}
