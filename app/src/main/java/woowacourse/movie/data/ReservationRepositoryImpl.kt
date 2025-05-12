package woowacourse.movie.data

import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.domain.model.reservation.ReservationHistory
import woowacourse.movie.domain.model.ticketing.Ticket

class ReservationRepositoryImpl(
    private val dao: ReservationDao,
) : ReservationRepository {
    override fun getAll(): List<ReservationHistory> = dao.getAll().toReservationHistory()

    override fun insert(ticket: Ticket): Result<Unit> = runCatching { dao.insert(ticket.toEntity()) }

    private fun List<ReservationEntity>.toReservationHistory() =
        this.map { entity ->
            ReservationHistory(entity.id, entity.toTicket())
        }

    private fun ReservationEntity.toTicket() = Ticket(
        title, theaterName, dateTime, seats, totalPrice
    )

    private fun Ticket.toEntity() =
        ReservationEntity(
            this.generateUniqueId(),
            this.title,
            this.theaterName,
            this.reservationDateTime,
            this.seats,
            this.count,
            this.price,
        )

    private fun Ticket.generateUniqueId(): Long {
        val rawId =
            "${this.title}-${this.theaterName}-${this.reservationDateTime}-${this.seats.joinToString()}"
        return rawId.hashCode().toLong()
    }
}
