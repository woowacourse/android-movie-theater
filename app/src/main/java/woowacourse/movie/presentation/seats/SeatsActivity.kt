package woowacourse.movie.presentation.seats

import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.common.BaseActivity
import woowacourse.movie.common.constant.IntentKeys
import woowacourse.movie.common.util.TicketUiFormatter
import woowacourse.movie.common.util.intentSerializable
import woowacourse.movie.databinding.ActivitySeatsBinding
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.presentation.result.BookingResultActivity
import java.io.Serializable

class SeatsActivity :
    BaseActivity<ActivitySeatsBinding>(R.layout.activity_seats),
    SeatsContract.View {
    private lateinit var movieTicket: MovieTicket
    private lateinit var presenter: SeatsContract.Presenter
    private var confirmDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchTicketFromIntent()) return
        presenter = SeatsPresenter(this, movieTicket)
        presenter.onViewCreated()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedSeats = presenter.getSelectedSeats()
        outState.putSerializable(SEATS_KEY, selectedSeats as Serializable)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedSeats = savedInstanceState.getSerializable(SEATS_KEY) as List<Seat>
        presenter.onConfigurationChanged(selectedSeats)
    }

    override fun initSeats() {
        binding.tablelayoutSeats.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children.filterIsInstance<TextView>().forEachIndexed { colIndex, view ->
                    val seat = presenter.getSeat(colIndex, rowIndex)
                    view.tag = seat
                    setSeatClickListener(view, seat)
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

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateAmount(amount: Int) {
        binding.textviewAmount.text =
            TicketUiFormatter.formatAmount(getString(R.string.amount_message), amount)
    }

    override fun updateSelectedSeats(seats: List<Seat>) {
        binding.tablelayoutSeats.children.filterIsInstance<TableRow>().forEach { row ->
            row.children.filterIsInstance<TextView>().forEach seat@{ seatView ->
                val seat = seatView.tag as? Seat ?: return@seat
                seatView.setBackgroundColor(
                    if (presenter.isSelectedSeat(seat)) {
                        getColor(R.color.selected_seat)
                    } else {
                        getColor(
                            R.color.white,
                        )
                    },
                )
            }
        }
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
        val intent =
            Intent(this, BookingResultActivity::class.java).apply {
                putExtra(IntentKeys.TICKET, ticket)
            }
        startActivity(intent)
    }

    private fun setSeatClickListener(
        view: TextView,
        seat: Seat,
    ) {
        view.setOnClickListener {
            presenter.onSeatClicked(seat)
            if (presenter.isSelectedSeat(seat)) {
                view.setBackgroundColor(getColor(R.color.selected_seat))
            } else {
                view.setBackgroundColor(getColor(R.color.white))
            }
        }
    }

    private fun fetchTicketFromIntent(): Boolean {
        val data = intent.intentSerializable(IntentKeys.TICKET, MovieTicket::class.java)
        if (data == null) {
            Toast.makeText(this, TICKET_INTENT_ERROR, Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        movieTicket = data
        return true
    }

    private fun initConfirmDialog() {
        confirmDialog =
            AlertDialog
                .Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_message))
                .setPositiveButton(getString(R.string.complete)) { _, _ -> presenter.onConfirmClicked() }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                .setCancelable(false)
                .create()
    }

    companion object {
        private const val TICKET_INTENT_ERROR = "[ERROR] 예매 정보에 대한 키 값이 올바르지 않습니다."
        private const val SEATS_KEY = "Seats"
    }
}
