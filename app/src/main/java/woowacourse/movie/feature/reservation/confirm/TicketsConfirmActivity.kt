package woowacourse.movie.feature.reservation.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.feature.BackKeyActionBarActivity
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.feature.DecimalFormatters
import woowacourse.movie.feature.reservation.MovieDetailActivity.Companion.KEY_TICKETS
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class TicketsConfirmActivity : BackKeyActionBarActivity(), TicketsConfirmContract.View {

    private val titleTextView: TextView by lazy { findViewById(R.id.reservation_title) }
    private val dateTextView: TextView by lazy { findViewById(R.id.reservation_date) }
    private val moneyTextView: TextView by lazy { findViewById(R.id.reservation_money) }
    private val reservationCountTextView: TextView by lazy { findViewById(R.id.reservation_count_and_seat) }

    private lateinit var tickets: TicketsState
    private lateinit var presenter: TicketsConfirmContract.Presenter

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_reservation_confirm)
        init()
        setViewsContent()
    }

    private fun init() {
        tickets = intent.getParcelableExtraCompat(KEY_TICKETS) ?: return keyError(KEY_TICKETS)
        presenter = TicketsConfirmPresenter(this)
    }

    private fun setViewsContent() {
        setTitle()
        setScreeningDateTime()
        setTicketSeatInfo()
        presenter.setDiscountApplyMoney(tickets)
    }

    override fun setTitle() { titleTextView.text = tickets.movieState.title }

    override fun setScreeningDateTime() {
        dateTextView.text = DateTimeFormatters.convertToDateTime(tickets.dateTime)
    }

    override fun setTicketSeatInfo() {
        reservationCountTextView.text =
            getString(
                R.string.person_count_and_seat,
                tickets.positions.size,
                tickets.positions.joinToString { it.toString() }
            )
    }

    override fun setMoney(value: Int) {
        moneyTextView.text = DecimalFormatters.convertToMoneyFormat(MoneyState(value))
    }

    companion object {
        fun startActivity(context: Context, tickets: TicketsState) {
            val intent = Intent(context, TicketsConfirmActivity::class.java).apply {
                putExtra(KEY_TICKETS, tickets)
            }
            context.startActivity(intent)
        }
    }
}
