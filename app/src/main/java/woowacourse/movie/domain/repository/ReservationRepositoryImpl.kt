package woowacourse.movie.domain.repository

import android.util.Log
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.db.reservationdb.ReservationEntity.Companion.toEntity
import woowacourse.movie.domain.model.Reservation

class ReservationRepositoryImpl(private val dao: ReservationDao) : ReservationRepository {
    override fun saveReservation(reservation: Reservation): Result<Long> {
        return runCatching {
            dao.insert(
                reservation.toEntity(),
            )
        }.onSuccess {
            Log.d("테스트", "new reservationId = $it")
        }.onFailure { e ->
            Log.d("테스트", "${e.message}")
        }
    }

    override fun findByReservationId(id: Long): Result<Reservation> {
        return runCatching {
            val entity = dao.getData(id) ?: throw IllegalArgumentException()
            entity.toDomain()
        }
    }

    override fun findAll(): Result<List<Reservation>> {
        return runCatching {
            dao.getAll().map { entity ->
                entity.toDomain()
            }
        }
    }
}
