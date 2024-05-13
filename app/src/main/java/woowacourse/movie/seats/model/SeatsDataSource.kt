package woowacourse.movie.seats.model

import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.ticket.model.TicketEntity

object SeatsDataSource {
    var date: String = ""
    var time: String = ""
    var seatTotalPrice = 0
    val selectedSeats
        get() = Seat.seats.filter { it.selected }
    var movieId: Long = -1
    var theaterId: Long = -1
    var ticketCount: Int = -1
    var seat: Seat = Seat.of(1, 1)

    val ticketData
        get() = TicketEntity(
            0,
            CommonDataSource.movieList.first { it.id == movieId }.title,
            date,
            time,
            selectedSeats.size,
            selectedSeats.joinToString { it.coordinate },
            TheaterData.theaters.first { it.id == theaterId }.name,
            seatTotalPrice,
        )
}
