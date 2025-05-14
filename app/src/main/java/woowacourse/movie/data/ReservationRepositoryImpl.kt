package woowacourse.movie.data

import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.data.mapper.toEntity
import woowacourse.movie.data.mapper.toTicket
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.domain.model.reservation.ReservationHistory
import woowacourse.movie.domain.model.ticketing.Ticket
import kotlin.concurrent.thread

class ReservationRepositoryImpl(
    private val dao: ReservationDao,
) : ReservationRepository {
    override fun getAll(onResult: (List<ReservationHistory>) -> Unit) {
        thread { onResult(dao.getAll().toReservationHistory()) }
    }

    override fun insert(ticket: Ticket, onResult: (Result<Long>) -> Unit) {
        thread {
            val result = runCatching { dao.insert(ticket.toEntity()) }
            onResult(result)
        }
    }

    private fun List<ReservationEntity>.toReservationHistory() =
        this.map { entity ->
            ReservationHistory(entity.id, entity.toTicket())
        }
}
