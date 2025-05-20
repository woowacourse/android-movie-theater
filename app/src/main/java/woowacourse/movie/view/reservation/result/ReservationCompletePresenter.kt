package woowacourse.movie.view.reservation.result

import woowacourse.movie.data.mapper.toUi
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seats

class ReservationCompletePresenter(
    val view: ReservationCompleteContract.View,
) : ReservationCompleteContract.Presenter {
    override fun fetchData(ticket: Ticket) {
        view.showTicketInfo(ticket.toUi())
        view.showSeatsInfo(ticket.seats.toSeatString())
        view.showTicketMoney(ticket.seats.reservationPrice())
        println("seat : ${ticket.seats.all.joinToString("|")}")
    }

    private fun Seats.toSeatString(): String {
        val seats = this.all.map { getSeatName(it.position) }.toSortedSet()
        return seats.joinToString(", ")
    }

    private fun getSeatName(position: Position): String {
        val columnChar = 'A' + position.row
        return "$columnChar${position.column + 1}"
    }
}
