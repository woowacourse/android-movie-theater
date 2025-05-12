package woowacourse.movie.domain

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationFetcher {
    fun fetchAll(onComplete: (List<ReservationInfo>) -> Unit)
}
