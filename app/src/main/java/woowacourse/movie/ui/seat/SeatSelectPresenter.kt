package woowacourse.movie.ui.seat

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.data.TicketsRepository
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

    override fun discountApply(tickets: TicketsState) {
        view.showMoneyText(discountApplyUseCase(tickets.asDomain()).asPresentation())
    }

    override fun addTicket(tickets: TicketsState) {
        TicketsRepository.addTicket(tickets)
        view.navigateToConfirmView(tickets)
    }

    override fun getTickets(seats: List<SeatPositionState>): TicketsState {
        return TicketsState(cinemaName, seatSelectState.movieState, seatSelectState.dateTime, seats)
    }

    override fun getRequireCount(): Int {
        return seatSelectState.countState.value
    }
}
