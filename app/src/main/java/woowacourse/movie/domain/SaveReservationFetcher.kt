package woowacourse.movie.domain

import woowacourse.movie.domain.model.ReservationInfo

interface SaveReservationFetcher {
    fun saveReservation(reservation: ReservationInfo)
}
