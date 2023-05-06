package woowacourse.movie.ui.seat

import com.example.domain.repository.TicketsRepository
import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class SeatSelectPresenter(
    private val view: SeatSelectContract.View
) : SeatSelectContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()

    private lateinit var seatSelectState: SeatSelectState
    private lateinit var cinemaName: String

    fun init(seatSelectState: SeatSelectState, cinemaName: String) {
        this.seatSelectState = seatSelectState
        this.cinemaName = cinemaName

        view.showSeatSelectState(seatSelectState)
        view.initSeatTable(seatSelectState)
    }

    override fun discountApply(positionStates: List<SeatPositionState>) {
        val tickets = TicketsState(
            cinemaName,
            seatSelectState.movieState,
            seatSelectState.dateTime,
            positionStates
        )
        view.setMoneyText(discountApplyUseCase(tickets.asDomain()).asPresentation())
    }

    override fun addTicket(positionStates: List<SeatPositionState>) {
        val tickets = TicketsState(
            cinemaName,
            seatSelectState.movieState,
            seatSelectState.dateTime,
            positionStates
        )
        TicketsRepository.addTicket(tickets.asDomain())
        view.navigateToConfirmView(tickets)
    }

    override fun getRequireCount(): Int {
        return seatSelectState.countState.value
    }
}
