package woowacourse.movie.presentation.ui.seatpicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import com.woowacourse.data.database.reservation.ReservationDatabase
import com.woowacourse.data.database.reservation.history.dao.ReservationDao
import com.woowacourse.data.datasource.history.local.LocalHistoryDataSource
import com.woowacourse.data.repository.history.local.LocalHistoryRepository
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatPickerBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.showToast
import woowacourse.movie.presentation.extensions.title
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.receiver.ReservationPushReceiver
import woowacourse.movie.presentation.ui.main.alarm.PushAlarmManager
import woowacourse.movie.presentation.ui.seatpicker.contract.SeatPickerContract
import woowacourse.movie.presentation.ui.seatpicker.presenter.SeatPickerPresenter
import woowacourse.movie.presentation.ui.ticketingresult.TicketingResultActivity

class SeatPickerActivity :
    BaseActivity<ActivitySeatPickerBinding>(true),
    SeatPickerContract.View {
    override val layoutResId: Int = R.layout.activity_seat_picker
    override lateinit var presenter: SeatPickerContract.Presenter

    private val seatViews: MutableList<View> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = makePresenter()
        setClickListener()
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putParcelable(PICKED_SEATS_KEY, presenter.getState())
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        super.onRestoreInstanceState(bundle)
        val state = bundle.getParcelableCompat<PickedSeats>(PICKED_SEATS_KEY)
        state?.let { presenter.setState(it) }
    }

    override fun showMovieTitle(title: String) {
        binding.movieTitleTv.text = title
    }

    override fun updateReservationBtnEnabled(isEnabled: Boolean) {
        binding.doneBtn.isEnabled = isEnabled
    }

    override fun updateTotalPriceView(ticketPrice: TicketPrice) {
        binding.totalPriceTv.text =
            getString(R.string.movie_pay_price, ticketPrice.amount)
    }

    override fun initSeatTable(rowSize: Int, colSize: Int) {
        SeatRow.make(rowSize).forEach { seatRow ->
            binding.seatTable.addView(makeSeatTableRow(seatRow, colSize))
        }
    }

    override fun fetchPickedSeatViews(pickedIndices: List<Int>) {
        clearSeatViewState()
        pickedIndices.forEach { index -> setSeatViewPickState(index, true) }
    }

    override fun setSeatViewPickState(index: Int, isPicked: Boolean) {
        seatViews.getOrNull(index)?.isSelected = isPicked
    }

    override fun showSeatExceedAlertMessage() {
        showToast(getString(R.string.exceed_pickable_seat))
    }

    private fun clearSeatViewState() {
        seatViews.forEach { it.isSelected = false }
    }

    private fun makeSeatTableRow(row: SeatRow, colSize: Int): TableRow = TableRow(this).apply {
        SeatColumn.make(colSize).forEach { col ->
            val seatView = makeSeatView(row, col)
            addView(seatView)
            seatViews.add(seatView)
        }
    }

    private fun makeSeatView(row: SeatRow, col: SeatColumn): View {
        val seat = Seat(row, col)
        return seat.makeView(this) {
            presenter.changeSeatState(seat)
        }
    }

    override fun registerPushBroadcast(reservation: Reservation) {
        val alarmIntent = Intent(this, ReservationPushReceiver::class.java)
        val alarmManager = PushAlarmManager(this)

        alarmManager.set(
            intent = alarmIntent,
            pushData = reservation,
            time = reservation.reservedTime.minusMinutes(REMINDER_TIME_MINUTES_AGO),
        )
    }

    override fun showTicketingResultScreen(reservation: Reservation) {
        startActivity(TicketingResultActivity.getIntent(this, reservation, false))
        finish()
    }

    private fun setClickListener() {
        binding.doneBtn.setOnClickListener { showTicketingConfirmDialog() }
    }

    private fun showTicketingConfirmDialog() {
        createAlertDialog(false) {
            title(getString(R.string.ticketing_confirm_title))
            message(getString(R.string.ticketing_confirm_message))
            positiveButton(getString(R.string.ticketing_confirm_positive_btn)) { presenter.reserveMovie() }
            negativeButton(getString(R.string.ticketing_confirm_negative_btn)) { it.dismiss() }
        }.show()
    }

    private fun makePresenter(): SeatPickerContract.Presenter = SeatPickerPresenter(
        view = this,
        ticketingState = intent.getParcelableCompat(TICKETING_STATE_KEY)!!,
        historyRepository = LocalHistoryRepository(
            LocalHistoryDataSource(ReservationDao(ReservationDatabase(this)))
        )
    )

    companion object {
        internal const val PICKED_SEATS_KEY = "picked_seats_key"
        internal const val REMINDER_TIME_MINUTES_AGO = 30L

        private const val TICKETING_STATE_KEY = "ticketing_state_key"

        fun getIntent(context: Context, ticketingState: TicketingState): Intent =
            Intent(context, SeatPickerActivity::class.java)
                .putExtra(TICKETING_STATE_KEY, ticketingState)
    }
}
