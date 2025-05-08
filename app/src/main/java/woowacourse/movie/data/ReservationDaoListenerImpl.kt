package woowacourse.movie.data

import woowacourse.movie.domain.model.ticketing.Ticket

class ReservationDaoListenerImpl(
    private val dao: ReservationDao,
) : ReservationDaoListener {
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
                entity.reservationCount,
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
        )
}
