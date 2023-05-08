package woowacourse.movie.view.seatselect

import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TicketsUiModel

interface SelectSeatContract {
    interface Presenter {
        fun onClickSeat(seatView: SeatView)
        fun onSeatStateChange()
        fun onPriceTextChange()
        fun onCheckButtonStateChange()
        fun onClickCheckButton()
        fun onClickDialogPositiveButton()
        fun onClickDialogCancelButton()
    }

    interface View {
        val presenter: Presenter
        fun updateSeats(ticketsUiModel: TicketsUiModel)
        fun setPriceText(price: Int)
        fun setCheckButtonClickable(isClickable: Boolean)
        fun setCheckButtonColor(condition: Boolean)
        fun startReservationResultActivity(
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel
        )

        fun registerAlarm(movieUiModel: MovieUiModel, ticketsUiModel: TicketsUiModel)
        fun showDialog()
        fun cancelDialog()
    }
}
