package woowacourse.movie.data

import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsRepository
import woowacourse.movie.data.sqlite.ReservationTicketsDao

class TicketsRepositoryImpl(
    private val reservationTicketsDao: ReservationTicketsDao
) : TicketsRepository {
    override fun allTickets(): List<Tickets> {
        return reservationTicketsDao.selectAllTickets()
    }

    override fun addTickets(tickets: Tickets) {
        reservationTicketsDao.insertTickets(tickets)
    }
}
