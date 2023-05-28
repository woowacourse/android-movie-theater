package woowacourse.movie.ui.seat

import android.widget.TableRow
import woowacourse.movie.uimodel.MovieTicketModel
import woowacourse.movie.uimodel.SeatModel
import woowacourse.movie.uimodel.SelectedSeatsModel

interface SeatSelectionContract {
    interface View {
        val presenter: Presenter

        fun initMovieTitleView(title: String)
        fun updatePriceText(price: Int)
        fun updateButtonEnablement(isSelectionDone: Boolean)
        fun makeAlarm(ticket: MovieTicketModel)
        fun showErrorMessage()
        fun initSeat(tableRow: TableRow, seat: SeatModel, isSelected: Boolean)
        fun selectSeat(view: android.view.View)
    }

    interface Presenter {
        fun updateSelectedSeatsModel(selectedSeatsModel: SelectedSeatsModel)
        fun addSeat(tableRow: TableRow, row: Int, column: Int)
        fun clickSeat(seat: SeatModel, seatView: android.view.View)
        fun addReservation(ticket: MovieTicketModel)
        fun makeAlarm(ticket: MovieTicketModel)
    }
}
