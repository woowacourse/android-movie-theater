package woowacourse.movie.data

import woowacourse.movie.GlobalApplication
import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.domain.model.ticketing.Ticket

class ReservationRepositoryImpl(
    private val dao: ReservationDao =
        ReservationDatabase
            .getInstance(GlobalApplication.instance)
            .reservationDao(),
) : ReservationRepository {
    override fun getAll(): List<Ticket> = dao.getAll().toDomain()

    override fun insert(vararg ticket: Ticket) {
        dao.insert(*ticket.map { it.toEntity() }.toTypedArray())
    }

    private fun List<ReservationEntity>.toDomain() =
        this.map { entity ->
            Ticket(
                entity.title,
                entity.theaterName,
                entity.dateTime,
                entity.seats,
                entity.totalPrice,
            )
        }

    private fun Ticket.toEntity() =
        ReservationEntity(
            this.title,
            this.theaterName,
            this.reservationDateTime,
            this.seats,
            this.count,
            this.price,
            this.generateUniqueId(),
        )

    private fun Ticket.generateUniqueId(): String = "${this.title}-${this.theaterName}-${this.reservationDateTime}-${this.seats.hashCode()}"
}
