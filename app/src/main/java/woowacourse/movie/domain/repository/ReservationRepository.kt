package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationRepository {
    fun getAllReservations(): List<ReservationInfo>

    fun saveReservation(reservation: ReservationInfo)
}
