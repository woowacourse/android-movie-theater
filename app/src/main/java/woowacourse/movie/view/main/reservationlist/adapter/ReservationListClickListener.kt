package woowacourse.movie.view.main.reservationlist.adapter

import woowacourse.movie.model.reservation.ReservationInfo

fun interface ReservationListClickListener {
    fun onReservationInfoClick(reservationInfo: ReservationInfo)
}
