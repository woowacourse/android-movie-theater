package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectBinding
import woowacourse.movie.dbHelper.TicketsDbHelper
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
    override lateinit var presenter: SeatSelectContract.Presenter
    override lateinit var binding: ActivitySeatSelectBinding

    private lateinit var seatTable: SeatTable

    override fun onCreateView(savedInstanceState: Bundle?) {
        presenter.setUpSeatSelectState()
    }

    override fun initBinding() {
        binding = ActivitySeatSelectBinding.inflate(layoutInflater)
    }

    override fun initPresenter() {
        presenter = SeatSelectPresenter(
            this,
            TicketsDbHelper(this),
            intent.getParcelableExtraCompat(KEY_SEAT_SELECT) ?: return keyError(KEY_SEAT_SELECT),
            intent.getStringExtra(KEY_CINEMA_NAME) ?: return keyError(KEY_CINEMA_NAME)
        )
    }

    override fun initSeatTable(seatSelectState: SeatSelectState) {
        seatTable = SeatTable(binding, seatSelectState.countState) {
            presenter.discountMoneyApply(it)
        }
    }

    override fun showMoneyText(money: MoneyState) {
        binding.reservationMoney.text = getString(
            R.string.discount_money,
            DecimalFormatters.convertToMoneyFormat(money)
        )
    }

    override fun showReservationTitle(seatSelectState: SeatSelectState) {
        binding.reservationTitle.text = seatSelectState.movieState.title
    }

    override fun setConfirmClickable(clickable: Boolean) {
        binding.reservationConfirm.isClickable = clickable
    }

    override fun setReservationConfirm(seatSelectState: SeatSelectState) {
        binding.reservationConfirm.setOnClickListener {
            navigateShowDialog(seatTable.chosenSeatInfo)
        }
        binding.reservationConfirm.isClickable = false
    }

    override fun navigateToConfirmView(tickets: TicketsState) {
        ReservationConfirmActivity.startActivity(this, tickets)
    }

    private fun navigateShowDialog(positionStates: List<SeatPositionState>) {
        showAskDialog(
            titleId = R.string.reservation_confirm,
            messageId = R.string.ask_really_reservation,
            negativeStringId = R.string.reservation_cancel,
            positiveStringId = R.string.reservation_complete
        ) {
            presenter.addTicket(positionStates)
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreState: ArrayList<SeatPositionState> =
            savedInstanceState.getParcelableArrayListCompat(KEY_SEAT_RESTORE)
                ?: return keyError(KEY_SEAT_RESTORE)
        seatTable.chosenSeatUpdate(restoreState.toList())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            KEY_SEAT_RESTORE,
            ArrayList(seatTable.chosenSeatInfo)
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
