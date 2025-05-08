package woowacourse.movie.domain

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationProvider {
    fun getAllReservations(): List<ReservationInfo>

    fun saveReservation(reservation: ReservationInfo)
}
