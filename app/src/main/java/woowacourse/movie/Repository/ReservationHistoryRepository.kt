package woowacourse.movie.Repository

import woowacourse.movie.App
import woowacourse.movie.db.ReservationInfoDao
import woowacourse.movie.db.ReservationInfoEntity
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import kotlin.concurrent.thread

class ReservationHistoryRepositoryImpl private constructor(
    private val reservationInfoDao: ReservationInfoDao,
) : ReservationHistoryRepository {
    override fun getAllReservationHistories(callback: (List<ReservationInfo>) -> Unit) {
        thread {
            val result = reservationInfoDao.getAll()
            callback(result.map { it.toDomain() })
        }
    }

    private fun ReservationInfoEntity.toDomain() =
        ReservationInfo(
            this.title,
            this.reservationDateTime,
            ReservationCount(this.reservationCount),
            this.seats,
            Cinema(0, this.cinema),
        )

    companion object {
        fun newInstance(): ReservationHistoryRepositoryImpl {
            val dao = App.instance.database.reservationInfoDao()
            return ReservationHistoryRepositoryImpl(dao)
        }
    }
}
