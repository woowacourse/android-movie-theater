package woowacourse.movie.view.seatselect

import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.SeatRankUiModel
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.model.TicketsUiModel

interface SelectSeatContract {
    interface Presenter {
        fun updateSeatsRank(seatUiModels: List<SeatUiModel>)
        fun updateTickets(seatUiModel: SeatUiModel)
        fun calculatePrice()
        fun changeButtonState()
        fun completeReservation()
        fun showResult()
        fun registerAlarm()
        fun saveReservation()
    }

    interface View {
        fun setSeatsTextColor(seatRanks: List<SeatRankUiModel>)
        fun setSeatsBackgroundColor(ticketsUiModel: TicketsUiModel)
        fun setPriceText(price: Int)
        fun setCheckButtonClickable(isClickable: Boolean)
        fun setCheckButtonColorBy(condition: Boolean)
        fun showResultScreen(
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel,
        )

        fun setAlarm(movieUiModel: MovieUiModel, ticketsUiModel: TicketsUiModel)
        fun askConfirmReservation()
    }
}
