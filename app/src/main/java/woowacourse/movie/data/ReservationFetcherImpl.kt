package woowacourse.movie.data

import woowacourse.movie.domain.ReservationFetcher
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationFetcherImpl(
    private val reservationRepository: ReservationRepository,
) : ReservationFetcher {
    override fun fetchAll(onComplete: (List<ReservationInfo>) -> Unit) {
        thread {
            val reservations = reservationRepository.getAllReservations()
            onComplete(reservations)
        }
    }
}
