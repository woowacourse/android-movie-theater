package woowacourse.movie.data.repository

import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationMapper.toDomain
import woowacourse.movie.data.db.ReservationMapper.toEntity
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationRepositoryImpl(
    private val dao: ReservationDao,
) : ReservationRepository {
    override fun getAllReservations(onComplete: (List<ReservationInfo>) -> Unit) {
        thread {
            val reservations = dao.getAllReservation().map { it.toDomain() }
            onComplete(reservations)
        }
    }

    override fun saveReservation(reservation: ReservationInfo) {
        thread {
            dao.saveReservation(reservation.toEntity())
        }
    }
}
