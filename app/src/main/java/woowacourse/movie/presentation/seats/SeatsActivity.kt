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
import woowacourse.movie.common.BaseActivity
import woowacourse.movie.common.util.getSerializableCompat
import woowacourse.movie.common.util.getSerializableExtraCompat
import woowacourse.movie.databinding.ActivitySeatsBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.presentation.result.BookingResultActivity
import woowacourse.movie.presentation.seats.mode.SeatUiModel
import woowacourse.movie.presentation.seats.mode.toDomain
import woowacourse.movie.presentation.seats.mode.toUiModel

class SeatsActivity :
    BaseActivity<ActivitySeatsBinding>(R.layout.activity_seats),
    SeatsContract.View {
    private lateinit var presenter: SeatsPresenter
    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchTicket()) return
        presenter = SeatsPresenter(this, ticket)
        initView()
        presenter.loadSeatSelect()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(EXTRA_TICKET, presenter.ticket)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredTicket =
            savedInstanceState.getSerializableCompat(EXTRA_TICKET, Ticket::class.java)
        restoredTicket?.let { presenter.restoreTicket(it) }
    }

    override fun showMovieInfo(movie: Movie) {
        binding.movie = movie
    }

    override fun showTotalPrice(price: Int) {
        binding.price = price
    }

    override fun updateSeatSelectionState(
        seat: Seat,
        isSelected: Boolean,
    ) {
        val seatTextView: TextView = binding.tablelayoutSeats.findViewWithTag(seat.toUiModel())
        seatTextView.setBackgroundResource(if (isSelected) R.color.yellow else R.color.white)
    }

    override fun updateConfirmButtonState(isEnabled: Boolean) {
        binding.buttonConfirm.isEnabled = isEnabled
    }

    override fun navigateToSummary(ticket: Ticket) {
        val intent = BookingResultActivity.newIntent(this, ticket)
        startActivity(intent)
    }

    private fun fetchTicket(): Boolean {
        val data = intent.getSerializableExtraCompat(EXTRA_TICKET, Ticket::class.java)
        if (data == null) {
            Toast.makeText(this, ERROR_INTENT_KEY, Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        ticket = data
        return true
    }

    private fun initView() {
        initSeats()
        initConfirmButton()
    }

    private fun initSeats() {
        binding.tablelayoutSeats.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIdx, row ->
                row.children.filterIsInstance<TextView>().forEachIndexed { colIdx, view ->
                    view.apply {
                        val seat = SeatUiModel(rowIdx, colIdx)
                        tag = seat
                        text = seat.toString()
                        setTextColor(getColor(seat.colorResId))
                        setOnClickListener { presenter.selectSeat(seat.toDomain()) }
                    }
                }
            }
    }

    private fun initConfirmButton() {
        binding.eventHandler =
            object : SeatSelectEventHandler {
                override fun onConfirmButtonClicked() {
                    showConfirmAlertDialog()
                }
            }
    }

    private fun showConfirmAlertDialog() {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(getString(R.string.complete)) { _, _ -> presenter.finishBooking() }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()
    }

    companion object {
        fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent =
            Intent(context, SeatsActivity::class.java).apply {
                putExtra(EXTRA_TICKET, ticket)
            }

        private const val EXTRA_TICKET = "ticket"
        private const val ERROR_INTENT_KEY = "[ERROR] 키 값이 올바르지 않습니다."
    }
}
