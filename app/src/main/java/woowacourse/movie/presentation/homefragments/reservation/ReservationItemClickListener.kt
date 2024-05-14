package woowacourse.movie.presentation.homefragments.reservation

import woowacourse.movie.model.Reservation

interface ReservationItemClickListener {
    fun onClickReservationItem(reservation: Reservation)
}
