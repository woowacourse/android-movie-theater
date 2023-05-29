package woowacourse.movie.feature.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import androidx.core.view.children
import com.example.domain.model.TicketOffice
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.databinding.ActivitySeatSelectBinding
import woowacourse.movie.feature.BackKeyActionBarActivity
import woowacourse.movie.feature.DecimalFormatters
import woowacourse.movie.feature.common.customView.SeatView
import woowacourse.movie.feature.notification.MovieReminder
import woowacourse.movie.feature.reservation.confirm.TicketsConfirmActivity
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableArrayListCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError
import woowacourse.movie.util.showAskDialog
import kotlin.collections.ArrayList

class SeatSelectActivity : BackKeyActionBarActivity(), SeatSelectContract.View {
    private var _binding: ActivitySeatSelectBinding? = null
    private val binding: ActivitySeatSelectBinding get() = _binding!!

    private val presenter: SeatSelectContract.Presenter by lazy {
        val reservationState: SelectReservationState? =
            intent.getParcelableExtraCompat(KEY_SELECT_RESERVATION)
        SeatSelectPresenter(this, reservationState, TicketOffice(), TicketsRepositoryImpl())
    }

    private val allSeats: List<SeatView> by lazy {
        binding.seats
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<SeatView>()
            .toList()
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        _binding = ActivitySeatSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.loadViewContents()
        initClickListener()
    }

    private fun initClickListener() {
        allSeats.forEachIndexed { index, seatView ->
            seatView.setOnClickListener {
                presenter.checkSeat(index)
            }
        }
        binding.reservationConfirm.setOnClickListener { presenter.showReservationConfirmationDialog() }
        binding.reservationConfirm.isClickable = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SEAT_RESTORE_KEY, ArrayList(presenter.seats))
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreState: ArrayList<SeatPositionState> =
            savedInstanceState.getParcelableArrayListCompat(SEAT_RESTORE_KEY) ?: return keyError(
                SEAT_RESTORE_KEY
            )
        presenter.updateChosenSeats(restoreState.toList())
    }

    override fun setViewContents(selectReservationState: SelectReservationState) {
        binding.movie = selectReservationState.movie
    }

    override fun changeSeatCheckedByIndex(index: Int) {
        allSeats[index].changeSelected()
    }

    override fun changePredictMoney(moneyState: MoneyState) {
        binding.reservationMoney.text = getString(
            R.string.discount_money, DecimalFormatters.convertToMoneyFormat(moneyState)
        )
    }

    override fun setConfirmClickable(clickable: Boolean) {
        binding.reservationConfirm.isClickable = clickable
    }

    override fun showReservationConfirmationDialog() {
        showAskDialog(
            titleId = R.string.reservation_confirm,
            messageId = R.string.ask_really_reservation,
            negativeStringId = R.string.reservation_cancel,
            positiveStringId = R.string.reservation_complete
        ) {
            presenter.reserveTickets()
        }
    }

    override fun showLoadError() = keyError(KEY_SELECT_RESERVATION)

    override fun finishActivity() = finish()

    override fun navigateReservationConfirm(tickets: TicketsState) {
        TicketsConfirmActivity.startActivity(this, tickets)
    }

    override fun setReservationAlarm(tickets: TicketsState) {
        MovieReminder.getInstance().set(this, tickets)
    }

    companion object {
        fun getIntent(context: Context, reservationState: SelectReservationState): Intent {
            val intent = Intent(context, SeatSelectActivity::class.java)
            intent.putExtra(KEY_SELECT_RESERVATION, reservationState)
            return intent
        }

        private const val KEY_SELECT_RESERVATION = "key_reservation"
        private const val SEAT_RESTORE_KEY = "seat_restore_key"
    }
}
