package woowacourse.movie.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.model.seat.Col
import woowacourse.movie.model.seat.Row
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.ui.model.TicketUiModel

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    private val presenter = SeatSelectionPresenter(this)
    private lateinit var binding: ActivitySeatSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_selection)
        setUi()

        val ticket = requireTicketOrFinish() ?: return
        presenter.initializeData(ticket)

        savedInstanceState?.let { bundle ->
            val seats = bundle.getString(KEY_SEATS)

            if (!seats.isNullOrBlank()) {
                presenter.restoreTicketData(seats)
            }
        }

        setupSeatClickListeners()
        setupConfirmButton()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun requireTicketOrFinish(): TicketUiModel? {
        val ticket =
            IntentCompat.getParcelableExtra(
                intent,
                KEY_TICKET,
                TicketUiModel::class.java,
            )
        if (ticket == null) {
            showToastErrorAndFinish(getString(R.string.booking_toast_message))
            return null
        }
        return ticket
    }

    private fun setupSeatClickListeners() {
        forEachSeat { row, col, seatView ->
            seatView.setOnClickListener {
                presenter.onSeatClicked(Seat(Row(row), Col(col)))
            }
        }
    }

    private fun forEachSeat(action: (row: Int, col: Int, seatView: TextView) -> Unit) {
        val tableLayout = binding.seatTable

        for (rowIndex in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(rowIndex) as? TableRow ?: continue

            for (colIndex in 0 until row.childCount) {
                val seat = row.getChildAt(colIndex) as? TextView ?: continue
                action(rowIndex, colIndex, seat)
            }
        }
    }

    private fun setupConfirmButton() {
        binding.btnBookingConfirm.setOnClickListener {
            if (it.isEnabled) {
                presenter.onButtonClicked()
            }
        }
    }

    override fun showTicket(ticket: TicketUiModel) {
        binding.ticket = ticket
    }

    override fun showSeatState(
        seat: Seat,
        isSelected: Boolean,
    ) {
        val tableLayout = binding.seatTable

        val rowView = tableLayout.getChildAt(seat.row.value) as? TableRow ?: return
        val seatView = rowView.getChildAt(seat.col.value) as? TextView ?: return

        val colorRes =
            if (isSelected) {
                R.color.seat_selected_background
            } else {
                R.color.seat_unselected_background
            }

        seatView.setBackgroundColor(ContextCompat.getColor(this, colorRes))
    }

    override fun showToastErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun setButtonEnabled(enabled: Boolean) {
        val confirmButton = binding.btnBookingConfirm
        confirmButton.isEnabled = enabled

        val colorRes =
            if (enabled) {
                R.color.btn_activate_background
            } else {
                R.color.btn_deactivate_background
            }
        confirmButton.setBackgroundResource(colorRes)
    }

    override fun showBookingAlertDialog(ticket: TicketUiModel) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dig_title))
            .setMessage(getString(R.string.dig_message))
            .setPositiveButton(getString(R.string.dig_btn_positive_message)) { _, _ ->
                startBookingCompleteActivity(ticket)
            }
            .setNegativeButton(getString(R.string.dig_btn_negative_message)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun startBookingCompleteActivity(ticket: TicketUiModel) {
        val intent = BookingCompleteActivity.createIntent(this, ticket)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val ticketUiModel = presenter.getCurrentTicketUiModel()
        if (ticketUiModel.seats.isNotBlank()) {
            outState.putString(KEY_SEATS, ticketUiModel.seats)
        }
    }

    companion object {
        private const val KEY_TICKET = "ticketUiData"
        private const val KEY_SEATS = "SEATS"

        fun createIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent {
            return Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(KEY_TICKET, ticket)
            }
        }
    }
}
