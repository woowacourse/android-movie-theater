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

    init {
        setUpSeatSelectState()
    }

    override fun setUpSeatSelectState() {
        view.initSeatTable(seatSelectState)
        view.showReservationTitle(seatSelectState)
        view.setReservationConfirm(seatSelectState)
    }

    override fun discountMoneyApply(positionStates: List<SeatPositionState>) {
        val tickets = TicketsState(
            cinemaName,
            seatSelectState.movieState.title,
            seatSelectState.dateTime,
            positionStates
        )
        view.showMoneyText(discountApplyUseCase(tickets.asDomain()).asPresentation())
        view.setConfirmClickable(positionStates.size == seatSelectState.countState.value)
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
}
