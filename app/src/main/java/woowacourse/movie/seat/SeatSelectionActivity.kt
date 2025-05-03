package woowacourse.movie.seat

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.ui.model.SeatUiModel
import woowacourse.movie.ui.model.TicketUiModel

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {
    private val presenter = SeatSelectionPresenter(this)
    private val seatViews: MutableMap<SeatUiModel, TextView> = mutableMapOf()
    private lateinit var binding: ActivitySeatSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_selection)
        setUi()

        presenter.initializeData(requireTicketOrFinish())

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

    private fun requireTicketOrFinish(): TicketUiModel {
        return IntentCompat.getParcelableExtra(
            intent,
            KEY_TICKET,
            TicketUiModel::class.java,
        )
    }

    private fun setupSeatClickListeners() {
        binding.seatTable.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<TextView>()
                .forEachIndexed { colIndex, view ->
                    view.text = getString(R.string.seat_point).format('A' + rowIndex, colIndex + 1)
                    seatViews[SeatUiModel(rowIndex, colIndex)] = view

                    view.setOnClickListener {
                        presenter.onSeatClicked(rowIndex, colIndex)
                    }
                }
        }
    }

    private fun setupConfirmButton() {
        val confirmButton = binding.btnBookingConfirm
        confirmButton.setOnClickListener {
            presenter.onButtonClicked()
        }
    }

    override fun showTicket(ticket: TicketUiModel) {
        binding.ticket = ticket
    }

    override fun showSeatState(seat: SeatUiModel) {
        val seatView = seatViews[seat] ?: return

        val currentColor = (seatView.background as? ColorDrawable)?.color
        val selectedColor = ContextCompat.getColor(this, R.color.seat_selected_background)
        val unselectedColor = ContextCompat.getColor(this, R.color.seat_unselected_background)

        if (currentColor == selectedColor) {
            seatView.setBackgroundColor(unselectedColor)
        } else {
            seatView.setBackgroundColor(selectedColor)
        }
    }

    override fun setButtonEnabled(shouldEnabled: Boolean) {
        val confirmButton = binding.btnBookingConfirm
        val colorRes =
            if (shouldEnabled) R.color.btn_activate_background else R.color.btn_deactivate_background
        confirmButton.setBackgroundColor(ContextCompat.getColor(this, colorRes))
        confirmButton.isEnabled = shouldEnabled
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
        val intent = BookingCompleteActivity.newIntent(this, ticket)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val selectedColor = ContextCompat.getColor(this, R.color.seat_selected_background)

        val selectedSeats =
            seatViews
                .filterValues { value -> (value.background as? ColorDrawable)?.color == selectedColor }
                .keys

        outState.putParcelableArrayList(KEY_SELECTED_SEATS, ArrayList(selectedSeats))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val selectedSeats: List<SeatUiModel> =
            savedInstanceState.getParcelableArrayList(KEY_SELECTED_SEATS) ?: return
        restoreSeats(selectedSeats)

        presenter.restoreSeats(selectedSeats)
    }

    private fun restoreSeats(seats: List<SeatUiModel>) {
        val selectedColor = ContextCompat.getColor(this, R.color.seat_selected_background)

        seats.forEach { seat ->
            seatViews[seat]?.setBackgroundColor(selectedColor)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val KEY_TICKET = "TICKET_DATA"
        private const val KEY_SELECTED_SEATS = "SELECTED_SEATS_DATA"

        fun newIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent =
            Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra(KEY_TICKET, ticket)
            }
    }
}
