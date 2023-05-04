package woowacourse.movie.ui.confirm

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationConfirmBinding
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.DateTimeFormatters
import woowacourse.movie.ui.DecimalFormatters

class ReservationConfirmView(
    private val binding: ActivityReservationConfirmBinding,
    val tickets: TicketsState
) : ReservationConfirmContract.View {
    private val presenter = ReservationConfirmPresenter(this)

    init {
        binding.reservationTitle.text = tickets.movieState.title
        binding.reservationDate.text = DateTimeFormatters.convertToDateTime(tickets.dateTime)
        binding.reservationCountAndSeat.text = binding.root.context.getString(
            R.string.person_count_and_seat,
            tickets.positions.size,
            tickets.positions.joinToString { it.toString() },
            tickets.cinemaName
        )
        presenter.setDiscountApplyMoney(tickets)
    }

    override fun setMoneyTextView(money: MoneyState) {
        binding.reservationMoney.text = DecimalFormatters.convertToMoneyFormat(money)
    }
}
