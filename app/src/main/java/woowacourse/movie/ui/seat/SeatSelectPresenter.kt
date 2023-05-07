package woowacourse.movie.ui.seat

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.dbHelper.TicketsDbHelper
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class SeatSelectPresenter(
    private val view: SeatSelectContract.View,
    private val ticketsDbHelper: TicketsDbHelper,
    private val seatSelectState: SeatSelectState,
    private val cinemaName: String
) : SeatSelectContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()

    override fun setUpSeatSelectState() {
        view.initSeatTable(seatSelectState)
        view.setSeatSelectState(seatSelectState)
    }

    override fun discountApply(positionStates: List<SeatPositionState>) {
        val tickets = TicketsState(
            cinemaName,
            seatSelectState.movieState.title,
            seatSelectState.dateTime,
            positionStates
        )
        view.setMoneyText(discountApplyUseCase(tickets.asDomain()).asPresentation())
    }

    override fun addTicket(positionStates: List<SeatPositionState>) {
        val tickets = TicketsState(
            cinemaName,
            seatSelectState.movieState.title,
            seatSelectState.dateTime,
            positionStates
        )
        ticketsDbHelper.insert(tickets)
        view.navigateToConfirmView(tickets)
    }

    override fun getRequireCount(): Int {
        return seatSelectState.countState.value
    }
}
