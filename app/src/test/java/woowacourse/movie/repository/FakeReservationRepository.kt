package woowacourse.movie.repository

import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.uimodel.MovieTicketModel

object FakeReservationRepository : ReservationRepository {
    val tickets = mutableListOf<MovieTicketModel>()

    override fun getData(): List<MovieTicketModel> {
        return tickets.toList()
    }

    override fun saveData(ticket: MovieTicketModel) {
        tickets.add(ticket)
    }
}
