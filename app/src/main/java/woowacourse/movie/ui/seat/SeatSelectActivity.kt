package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.ui.DecimalFormatters
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.ui.customView.ConfirmView
import woowacourse.movie.util.getParcelableArrayListCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError
import woowacourse.movie.util.showAskDialog

class SeatSelectActivity : BackKeyActionBarActivity(), SeatSelectContract.View {
    private val presenter = SeatSelectPresenter(this)

    private val titleTextView: TextView by lazy { findViewById(R.id.reservation_title) }
    private val moneyTextView: TextView by lazy { findViewById(R.id.reservation_money) }
    private val confirmView: ConfirmView by lazy { findViewById(R.id.reservation_confirm) }
    private lateinit var seatSelectState: SeatSelectState

    private lateinit var seatTable: SeatTable

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_seat_select)

        seatSelectState =
            intent.getParcelableExtraCompat(KEY_SEAT_SELECT) ?: return keyError(KEY_SEAT_SELECT)

        titleTextView.text = seatSelectState.movieState.title

        confirmView.setOnClickListener { navigateShowDialog(seatTable.chosenSeatInfo) }
        confirmView.isClickable = false // 클릭리스너를 설정하면 clickable이 자동으로 참이 되기 때문

        seatTable = SeatTable(window.decorView.rootView, seatSelectState.countState) {
            updateSelectSeats(it)
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreState: ArrayList<SeatPositionState> =
            savedInstanceState.getParcelableArrayListCompat(KEY_SEAT_RESTORE) ?: return keyError(
                KEY_SEAT_RESTORE
            )
        seatTable.chosenSeatUpdate(restoreState.toList())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            KEY_SEAT_RESTORE,
            ArrayList(seatTable.chosenSeatInfo)
        )
    }

    private fun navigateShowDialog(seats: List<SeatPositionState>) {
        showAskDialog(
            titleId = R.string.reservation_confirm,
            messageId = R.string.ask_really_reservation,
            negativeStringId = R.string.reservation_cancel,
            positiveStringId = R.string.reservation_complete
        ) {
            navigateReservationConfirmActivity(seats)
        }
    }

    private fun navigateReservationConfirmActivity(seats: List<SeatPositionState>) {
        val tickets = TicketsState.from(seatSelectState, seats)
        ReservationConfirmActivity.startActivity(this, tickets)
    }

    private fun updateSelectSeats(positionStates: List<SeatPositionState>) {
        confirmView.isClickable = (positionStates.size == seatSelectState.countState.value)

        val tickets = TicketsState(
            seatSelectState.movieState,
            seatSelectState.dateTime,
            positionStates.toList()
        )

        presenter.discountApply(tickets)
    }

    override fun setMoneyText(money: MoneyState) {
        moneyTextView.text = getString(
            R.string.discount_money,
            DecimalFormatters.convertToMoneyFormat(money)
        )
    }

    companion object {
        private const val KEY_SEAT_RESTORE = "key_seat_restore"
        private const val KEY_SEAT_SELECT = "key_seat_select"

        fun startActivity(context: Context, seatSelectState: SeatSelectState) {
            val intent = Intent(context, SeatSelectActivity::class.java).apply {
                putExtra(KEY_SEAT_SELECT, seatSelectState)
            }
            context.startActivity(intent)
        }
    }
}
