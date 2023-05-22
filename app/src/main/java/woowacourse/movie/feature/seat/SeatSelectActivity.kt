package woowacourse.movie.feature.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.example.domain.model.TicketOffice
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
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
    private lateinit var binding: ActivitySeatSelectBinding

    private lateinit var presenter: SeatSelectContract.Presenter

    private val allSeats by lazy {
        binding.seats
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<SeatView>()
            .toList()
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_select)
        val reservationState: SelectReservationState =
            intent.getParcelableExtraCompat(KEY_SELECT_RESERVATION) ?: return keyError(
                KEY_SELECT_RESERVATION
            )
        initClickListener()

        presenter = SeatSelectPresenter(
            this,
            reservationState,
            TicketOffice(),
            TicketsRepository
        )
        binding.movie = reservationState.movie
    }

    private fun initClickListener() {
        allSeats.forEachIndexed { index, seatView ->
            seatView.setOnClickListener {
                presenter.clickSeat(index)
            }
        }
        binding.reservationConfirm.setOnClickListener { presenter.clickConfirm() }
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

    override fun seatToggle(index: Int) {
        allSeats[index].toggle()
    }

    override fun changePredictMoney(moneyState: MoneyState) {
        binding.reservationMoney.text = getString(
            R.string.discount_money,
            DecimalFormatters.convertToMoneyFormat(moneyState)
        )
    }

    override fun setConfirmClickable(clickable: Boolean) {
        binding.reservationConfirm.isClickable = clickable
    }

    override fun showAskScreen() {
        showAskDialog(
            titleId = R.string.reservation_confirm,
            messageId = R.string.ask_really_reservation,
            negativeStringId = R.string.reservation_cancel,
            positiveStringId = R.string.reservation_complete
        ) {
            presenter.clickAskPageConfirm()
        }
    }

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
