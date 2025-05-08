package woowacourse.movie.presentation.view.reservationlist

import woowacourse.movie.presentation.model.ReservationInfoUiModel

interface ReservationClickListener {
    fun onReservationClick(reservation: ReservationInfoUiModel)
}
