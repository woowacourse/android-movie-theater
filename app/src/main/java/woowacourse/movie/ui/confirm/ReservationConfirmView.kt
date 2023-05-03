package woowacourse.movie.ui.confirm

import android.view.View
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.DateTimeFormatters
import woowacourse.movie.ui.DecimalFormatters

class ReservationConfirmView(
    val view: View,
    val tickets: TicketsState
) : ReservationConfirmContract.View {
    private val presenter = ReservationConfirmPresenter(this)

    private val titleTextView: TextView = view.findViewById(R.id.reservation_title)
    private val dateTextView: TextView = view.findViewById(R.id.reservation_date)
    private val moneyTextView: TextView = view.findViewById(R.id.reservation_money)
    private val reservationCountTextView: TextView = view.findViewById(
        R.id.reservation_count_and_seat
    )

    init {
        TicketsRepository.addTicket(tickets)
        setInitReservationData(tickets)
    }

    private fun setInitReservationData(
        tickets: TicketsState
    ) {
        titleTextView.text = tickets.movieState.title
        dateTextView.text = DateTimeFormatters.convertToDateTime(tickets.dateTime)
        reservationCountTextView.text = view.context.getString(
            R.string.person_count_and_seat,
            tickets.positions.size,
            tickets.positions.joinToString { it.toString() }
        )
        presenter.setDiscountApplyMoney(tickets)
    }

    override fun setMoneyTextView(money: MoneyState) {
        moneyTextView.text = DecimalFormatters.convertToMoneyFormat(money)
    }
}
