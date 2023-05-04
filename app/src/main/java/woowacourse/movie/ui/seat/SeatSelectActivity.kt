package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectBinding
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.ui.DecimalFormatters
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.util.getParcelableArrayListCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError
import woowacourse.movie.util.showAskDialog

class SeatSelectActivity : BackKeyActionBarActivity(), SeatSelectContract.View {
    private val presenter = SeatSelectPresenter(this)
    private lateinit var binding: ActivitySeatSelectBinding

    private lateinit var seatSelectState: SeatSelectState
    private lateinit var cinemaName: String

    private lateinit var seatTable: SeatTable

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivitySeatSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seatSelectState =
            intent.getParcelableExtraCompat(KEY_SEAT_SELECT) ?: return keyError(KEY_SEAT_SELECT)
        cinemaName =
            intent.getStringExtra(KEY_CINEMA_NAME) ?: return keyError(KEY_CINEMA_NAME)

        binding.reservationTitle.text = seatSelectState.movieState.title

        binding.reservationConfirm.setOnClickListener {
            navigateShowDialog(seatTable.chosenSeatInfo)
        }
        binding.reservationConfirm.isClickable = false // 클릭리스너를 설정하면 clickable이 자동으로 참이 되기 때문

        seatTable = SeatTable(binding, seatSelectState.countState) {
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
            val tickets = TicketsState.from(cinemaName, seatSelectState, seats)
            presenter.addTicket(tickets)
        }
    }

    override fun navigateToConfirmView(tickets: TicketsState) {
        ReservationConfirmActivity.startActivity(this, tickets)
    }

    private fun updateSelectSeats(positionStates: List<SeatPositionState>) {
        binding.reservationConfirm.isClickable = (positionStates.size == seatSelectState.countState.value)

        val tickets = TicketsState(
            cinemaName,
            seatSelectState.movieState,
            seatSelectState.dateTime,
            positionStates.toList()
        )

        presenter.discountApply(tickets)
    }

    override fun setMoneyText(money: MoneyState) {
        binding.reservationMoney.text = getString(
            R.string.discount_money,
            DecimalFormatters.convertToMoneyFormat(money)
        )
    }

    companion object {
        private const val KEY_SEAT_RESTORE = "key_seat_restore"
        private const val KEY_CINEMA_NAME = "key_cinema_name"
        private const val KEY_SEAT_SELECT = "key_seat_select"

        fun startActivity(context: Context, cinema: String, seatSelectState: SeatSelectState) {
            val intent = Intent(context, SeatSelectActivity::class.java).apply {
                putExtra(KEY_CINEMA_NAME, cinema)
                putExtra(KEY_SEAT_SELECT, seatSelectState)
            }
            context.startActivity(intent)
        }
    }
}
