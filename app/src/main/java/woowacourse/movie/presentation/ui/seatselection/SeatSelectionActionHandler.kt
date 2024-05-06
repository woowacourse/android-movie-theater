package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.SeatModel

interface SeatSelectionActionHandler {
    fun onSeatClicked(seatModel: SeatModel)

    fun onReservationButtonClicked()
}
