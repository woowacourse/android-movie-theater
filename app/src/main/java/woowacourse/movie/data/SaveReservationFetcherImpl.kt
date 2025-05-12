package woowacourse.movie.data

import woowacourse.movie.domain.SaveReservationFetcher
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class SaveReservationFetcherImpl(
    private val reservationRepository: ReservationRepository,
) : SaveReservationFetcher {
    override fun saveReservation(reservation: ReservationInfo) {
        thread {
            reservationRepository.saveReservation(reservation)
        }
    }
}
