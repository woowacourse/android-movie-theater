package woowacourse.movie.ui.seat

import woowacourse.movie.model.ReservationUiModel

interface TimeReminder {

    fun remind(reservation: ReservationUiModel)
}
