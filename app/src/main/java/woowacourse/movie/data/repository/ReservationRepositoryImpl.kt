package woowacourse.movie.data.repository

import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationMapper.toDomain
import woowacourse.movie.data.db.ReservationMapper.toEntity
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.repository.ReservationRepository

class ReservationRepositoryImpl(
    private val dao: ReservationDao,
) : ReservationRepository {
    override fun getAllReservations(): List<ReservationInfo> = dao.getAllReservation().map { it.toDomain() }

    override fun saveReservation(reservation: ReservationInfo) {
        dao.saveReservation(reservation.toEntity())
    }
}
