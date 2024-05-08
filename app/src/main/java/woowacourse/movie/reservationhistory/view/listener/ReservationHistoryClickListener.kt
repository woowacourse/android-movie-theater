package woowacourse.movie.reservationhistory.view.listener

import woowacourse.movie.data.db.ReservationHistoryEntity

interface ReservationHistoryClickListener {
    fun onReservationHistoryClick(reservationHistoryEntity: ReservationHistoryEntity)
}
