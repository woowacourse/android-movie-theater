package woowacourse.movie.Repository

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationHistoryRepository {
    fun getAllReservationHistories(callback: (List<ReservationInfo>) -> Unit)
}
