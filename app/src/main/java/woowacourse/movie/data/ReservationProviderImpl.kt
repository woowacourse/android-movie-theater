package woowacourse.movie.data

import woowacourse.movie.data.ReservationMapper.toDomain
import woowacourse.movie.data.ReservationMapper.toEntity
import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.domain.ReservationProvider
import woowacourse.movie.domain.model.ReservationInfo

class ReservationProviderImpl(
    private val dao: ReservationDao,
) : ReservationProvider {
    override fun getAllReservations(): List<ReservationInfo> = dao.getAllReservation().map { it.toDomain() }

    override fun saveReservation(reservation: ReservationInfo) {
        dao.saveReservation(reservation.toEntity())
    }
}
