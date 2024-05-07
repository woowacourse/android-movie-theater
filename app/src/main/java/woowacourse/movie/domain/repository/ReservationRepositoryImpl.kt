package woowacourse.movie.domain.repository

import android.util.Log
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.db.reservationdb.ReservationEntity

class ReservationRepositoryImpl(private val dao: ReservationDao) : ReservationRepository {
    override fun saveReservation(reservation: ReservationEntity): Result<Long> {
        return runCatching {
            dao.insert(reservation)
        }.onSuccess {
            Log.d("테스트", "new reservationId = $it")
        }.onFailure { e ->
            Log.d("테스트", "${e.message}")
        }
    }

    override fun findByReservationId(id: Long): Result<ReservationEntity> {
        return runCatching {
            dao.getData(id) ?: throw IllegalArgumentException()
        }
    }
}
