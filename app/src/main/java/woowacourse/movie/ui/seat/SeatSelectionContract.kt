package woowacourse.movie.ui.seat

import woowacourse.movie.uimodel.MovieTicketModel
import woowacourse.movie.uimodel.SeatModel
import woowacourse.movie.uimodel.SelectedSeatsModel

interface SeatSelectionContract {
    interface View {
        val presenter: Presenter

        fun initMovieTitleView(title: String)
        fun updatePriceText(price: Int)
        fun updateButtonEnablement(isSelectionDone: Boolean)
    }

    interface Presenter {
        val selectedSeatsModel: SelectedSeatsModel

        fun updateSelectedSeatsModel(selectedSeatsModel: SelectedSeatsModel)
        fun isAlarmSwitchOn(): Boolean
        fun clickSeat(seat: SeatModel, isSelected: Boolean)
        fun addReservation(ticket: MovieTicketModel)
        fun isSelected(seat: SeatModel): Boolean
        fun isSelectionDone(): Boolean
    }
}
