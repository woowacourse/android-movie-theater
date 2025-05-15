package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationRepository {
    fun getAllReservations(onComplete: (List<ReservationInfo>) -> Unit)

    fun saveReservation(reservation: ReservationInfo)
}
