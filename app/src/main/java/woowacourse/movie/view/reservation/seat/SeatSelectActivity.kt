package woowacourse.movie.view.reservation.seat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectBinding
import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.view.Extras
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.getParcelableExtraCompat
import woowacourse.movie.view.reservation.complete.ReservationCompleteActivity
import woowacourse.movie.view.reservation.detail.ReservationDetailDialog

@BindingAdapter("formattedPrice")
fun setFormattedPrice(
    view: TextView,
    totalPrice: Int,
) {
    val context = view.context
    val formatted =
        context
            .getString(R.string.seat_select_ticket_price)
            .format(ReservationUiFormatter.priceToUI(totalPrice))
    view.text = formatted
}

@BindingAdapter("enabledAlpha")
fun setButtonEnabledAlpha(
    button: Button,
    isEnabled: Boolean,
) {
    button.isClickable = isEnabled
    button.alpha = if (isEnabled) 1f else 0.1f
}

class SeatSelectActivity :
    AppCompatActivity(),
    SeatSelectContract.View {
    private lateinit var binding: ActivitySeatSelectBinding
    private val presenter: SeatSelectPresenter by lazy { SeatSelectPresenter(this) }
    private val reservationDialog by lazy { ReservationDetailDialog() }
    private val seatViews: MutableMap<String, TextView> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_select)
        ViewCompat.setOnApplyWindowInsetsListener(binding.svSeatSelect) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupSeatView(binding.tlSeat)
        presenter.fetchData {
            intent?.getParcelableExtraCompat<MovieTicket>(Extras.TicketData.TICKET_KEY)
        }

        setupConfirmButton()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showErrorDialog() {
    }

    override fun showReservationInfo(
        title: String,
        price: Int,
    ) {
        binding.movieTitle = title
        binding.totalPrice = price
    }

    override fun showSeatCountError(count: Int) {
        Toast
            .makeText(
                this,
                getString(R.string.seat_select_error_count, count),
                Toast.LENGTH_SHORT,
            ).show()
        return
    }

    override fun showSelectedSeat(seatId: String) {
        seatViews[seatId]?.setBackgroundResource(R.color.yellow)
    }

    override fun showDeselectedSeat(seatId: String) {
        seatViews[seatId]?.setBackgroundResource(R.color.white)
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
    }

    override fun updateConfirmButtonEnabled(isEnabled: Boolean) {
        binding.isConfirmButtonEnabled = isEnabled
    }

    override fun showReservationDialog(
        title: String,
        message: String,
    ) {
        reservationDialog.show(
            this,
            title,
            message,
            { dialog -> dialog.dismiss() },
            { _ ->
                presenter.createReservationInfo { reservationInfo ->
                    navigateToComplete(reservationInfo)
                }
            },
        )
    }

    override fun navigateToComplete(reservationInfo: ReservationInfo) {
        val intent =
            Intent(this, ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfo)
            }
        startActivity(intent)
        finish()
    }

    private fun setupSeatView(tableLayout: TableLayout) {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i)
            if (row is TableRow) {
                for (j in 0 until row.childCount) {
                    val seatView = row.getChildAt(j)
                    if (seatView is TextView) {
                        val seatId = seatView.text.toString()
                        seatViews[seatId] = seatView
                        seatView.setOnClickListener {
                            presenter.seatSelect(seatId)
                        }
                    }
                }
            }
        }
    }

    private fun setupConfirmButton() {
        binding.btnSeatSelectConfirm.apply {
            isClickable = false
            alpha = 0.1f
            setOnClickListener {
                presenter.confirmRequested(
                    getString(R.string.reservation_dialog_title),
                    getString(R.string.reservation_dialog_message),
                )
            }
        }
    }

    private fun setupSavedData(savedInstanceState: Bundle?) {
        val savedSeats =
            savedInstanceState?.getStringArrayList(Extras.SeatsData.SEATS_KEY)
                ?: emptyList<String>()
        presenter.restoreSelectedSeats(savedSeats)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(
            Extras.SeatsData.SEATS_KEY,
            ArrayList(presenter.getSelectedSeatIds()),
        )
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupSavedData(savedInstanceState)
        presenter.restoreButtonState()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
