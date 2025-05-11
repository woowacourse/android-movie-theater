package woowacourse.movie.domain

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationRepository {
    fun getAllReservations(): List<ReservationInfo>

    fun saveReservation(reservation: ReservationInfo)
}
