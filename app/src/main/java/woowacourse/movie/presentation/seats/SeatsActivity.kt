package woowacourse.movie.presentation.seats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.data.bookinghistory.BookingHistoryDatabase
import woowacourse.movie.databinding.ActivitySeatsBinding
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.SelectedSeats
import woowacourse.movie.presentation.bookingsummary.BookingSummaryActivity
import woowacourse.movie.presentation.DataBindingBaseActivity
import woowacourse.movie.presentation.notification.NotificationScheduler
import woowacourse.movie.presentation.util.TicketUiFormatter
import woowacourse.movie.presentation.util.getSerializableCompat
import woowacourse.movie.presentation.util.getSerializableExtraCompat

class SeatsActivity : DataBindingBaseActivity(), SeatsContract.View {
    private val binding by binding<ActivitySeatsBinding>(R.layout.activity_seats)
    private val presenter: SeatsPresenter by lazy {
        SeatsPresenter(
            this,
            BookingHistoryDatabase.getDatabase(),
            NotificationScheduler()
        )
    }
    private var confirmDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen(binding.root)
        if (!fetchTicketFromIntent()) return
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedSeats = presenter.selectedSeats
        outState.putSerializable(SELECTED_SEATS_KEY, selectedSeats)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedSeats = savedInstanceState.getSerializableCompat(SELECTED_SEATS_KEY, SelectedSeats::class.java)
        presenter.restoreSeats(selectedSeats)
    }

    override fun initSeats() {
        binding.tablelayoutSeats.children.filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children.filterIsInstance<TextView>().forEachIndexed { colIndex, view ->
                    val seat = Seat.of(colIndex, rowIndex)
                    view.tag = seat.seatPosition
                    view.setOnClickListener { presenter.selectSeat(seat) }
                }
            }
    }

    override fun showMovieTitle(title: String) {
        binding.textviewTitle.text = title
    }

    override fun showConfirmDialog() {
        if (confirmDialog == null) initConfirmDialog()
        confirmDialog?.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateAmount(amount: Int) {
        binding.textviewAmount.text =
            TicketUiFormatter.formatAmount(getString(R.string.amount_message), amount)
    }

    override fun updateSelectedSeat(
        seat: Seat,
        isSelected: Boolean,
    ) {
        val view = binding.tablelayoutSeats.findViewWithTag<TextView>(seat.seatPosition)
        view.setBackgroundResource(if (isSelected) R.color.selected_seat else R.color.white)
    }

    override fun updateConfirmButtonEnabled(canConfirm: Boolean) {
        val confirmTextView = binding.textviewConfirm
        if (canConfirm) {
            confirmTextView.setBackgroundColor(getColor(R.color.confirm_activate))
            confirmTextView.isClickable = true
            confirmTextView.setOnClickListener {
                showConfirmDialog()
            }
            return
        }
        confirmTextView.setBackgroundColor(getColor(R.color.confirm_deactivate))
        confirmTextView.isClickable = false
    }

    override fun navigateToSummary(ticket: MovieTicket) {
        val intent = BookingSummaryActivity.newIntent(this, ticket)
        startActivity(intent)
    }

    private fun fetchTicketFromIntent(): Boolean {
        val data = intent.getSerializableExtraCompat(SEATS_KEY, MovieTicket::class.java)
        if (data == null) {
            Toast.makeText(this, getString(R.string.ticket_intent_error), Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        presenter.initializeSeats(data)
        return true
    }

    private fun initConfirmDialog() {
        confirmDialog =
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_message))
                .setPositiveButton(getString(R.string.complete)) { _, _ -> presenter.publishMovieTicket() }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                .setCancelable(false)
                .create()
    }

    companion object {
        private const val SEATS_KEY = "Seats"
        private const val SELECTED_SEATS_KEY = "SelectedSeats"

        fun newIntent(
            context: Context,
            ticket: MovieTicket,
        ): Intent {
            return Intent(context, SeatsActivity::class.java).apply {
                putExtra(SEATS_KEY, ticket)
            }
        }
    }
}
