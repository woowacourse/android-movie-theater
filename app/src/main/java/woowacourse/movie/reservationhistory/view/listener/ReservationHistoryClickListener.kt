package woowacourse.movie.reservationhistory.view.listener

import woowacourse.movie.data.ReservationHistoryEntity

interface ReservationHistoryClickListener {
    fun onReservationHistoryClick(reservationHistoryEntity: ReservationHistoryEntity)
}
