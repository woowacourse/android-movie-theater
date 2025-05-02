package woowacourse.movie.seat

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
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
import woowacourse.movie.booking.complete.BookingCompleteActivity.Companion.KEY_BOOKING_RESULT
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
            ?: run {
                Log.e(TAG, ERROR_EMPTY_TICKET_DATA)
                showToastErrorAndFinish(getString(R.string.booking_toast_message))
                throw IllegalStateException(ERROR_FINISH_ACTIVITY.format(KEY_TICKET))
            }
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
        val confirmButton = findViewById<TextView>(R.id.btn_booking_confirm)
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

    override fun showToastErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun setButtonEnabled(enabled: Boolean) {
        val confirmButton = findViewById<TextView>(R.id.btn_booking_confirm)
        val colorRes =
            if (enabled) R.color.btn_activate_background else R.color.btn_deactivate_background
        confirmButton.setBackgroundColor(ContextCompat.getColor(this, colorRes))
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
        val intent =
            Intent(this, BookingCompleteActivity::class.java).apply {
                putExtra(KEY_BOOKING_RESULT, ticket)
            }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "SeatSelectionActivity"
        private const val ERROR_EMPTY_TICKET_DATA = "인텐트에 영화 예매 정보(KEY_TICKET)가 없습니다."
        private const val ERROR_FINISH_ACTIVITY = "%s 데이터가 없어서 Activity를 종료했습니다"
        const val KEY_TICKET = "ticketUiData"
    }
}
