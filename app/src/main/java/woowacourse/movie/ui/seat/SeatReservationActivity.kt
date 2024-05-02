package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatReservationBinding
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.DummySeats
import woowacourse.movie.ui.Currency
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import java.util.Locale

class SeatReservationActivity : AppCompatActivity(), SeatReservationContract.View {
    private val presenter = SeatReservationPresenter(this, DummyScreens(DummySeats()), DummyReservation)
    private val binding: ActivitySeatReservationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_seat_reservation,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_reservation)

        val timeReservationId = intent.getIntExtra(TIME_RESERVATION_ID, DEFAULT_TIME_RESERVATION_ID)
        val theaterId = intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)

        binding.presenter = presenter
        binding.theaterId = theaterId

        presenter.loadData(timeReservationId)
    }

    override fun initBinding(
        totalPrice: Int,
        timeReservation: TimeReservation,
    ) {
        binding.totalPrice = totalPrice
        binding.timeReservation = timeReservation
    }

    override fun updateTotalPrice(totalPrice: Int) {
        binding.tvSeatReservationTotalPrice.text = Currency.of(Locale.getDefault().country).format(totalPrice)
    }

    override fun showAllSeats(seats: Seats) {
        val gl = binding.glSeatReservationSeats
        val maxRow = seats.maxRow()
        val maxColumn = seats.maxColumn()

        gl.columnCount = maxColumn
        gl.rowCount = maxRow

        for (row in 0 until maxRow) {
            for (column in 0 until maxColumn) {
                val textView =
                    TextView(this).apply {
                        layoutParams =
                            GridLayout.LayoutParams().apply {
                                width = 0
                                height = 0
                                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                            }
                        setBackgroundResource(R.drawable.holder_seat_selector)
                        setOnClickListener {
                            presenter.selectSeat(position = Position(row, column), this)
                        }
                        gravity = Gravity.CENTER
                        text = "${'A' + row} ${column + 1}"
                    }
                gl.addView(textView)
            }
        }
    }

    override fun activateReservation(boolean: Boolean) {
        with(binding.btnSeatReservationComplete) {
            if (boolean) {
                isEnabled = true
                setBackgroundColor(getColor(R.color.complete_activated))
            } else {
                isEnabled = false
                setBackgroundColor(getColor(R.color.complete_deactivated))
            }
        }
    }

    override fun showSeatReservationFail(throwable: Throwable) {
        showToast(throwable)
    }

    override fun showDialog(
        reservationId: Int,
        theaterId: Int,
    ) {
        val alertDialog =
            AlertDialog.Builder(this)
                .setTitle(R.string.check_reservation_title)
                .setMessage(R.string.check_reservation_content)
                .setPositiveButton(R.string.check_reservation_complete) { _, _ ->
                    presenter.completeReservation(reservationId, theaterId)
                }
                .setNegativeButton(R.string.check_reservation_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()

        alertDialog.show()
    }

    override fun showToast(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToCompleteReservation(
        reservationId: Int,
        theaterId: Int,
    ) {
        ReservationCompleteActivity.startActivity(this, reservationId, theaterId)
    }

    companion object {
        private const val TIME_RESERVATION_ID = "timeReservationId"
        private const val PUT_EXTRA_THEATER_ID_KEY = "theaterId"

        private const val DEFAULT_TIME_RESERVATION_ID = -1
        private const val DEFAULT_THEATER_ID = -1

        fun startActivity(
            context: Context,
            timeReservationId: Int,
            theaterId: Int,
        ) {
            val intent =
                Intent(context, SeatReservationActivity::class.java).apply {
                    putExtra(TIME_RESERVATION_ID, timeReservationId)
                    putExtra(PUT_EXTRA_THEATER_ID_KEY, theaterId)
                }
            context.startActivity(intent)
        }
    }
}
