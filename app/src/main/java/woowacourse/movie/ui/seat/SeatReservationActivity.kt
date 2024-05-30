package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.movie.R
import woowacourse.movie.data.source.DummyScreenDataSource
import woowacourse.movie.data.source.DummySeatsDataSource
import woowacourse.movie.data.source.DummyTheatersDataSource
import woowacourse.movie.databinding.ActivitySeatReservationBinding
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.OfflineReservationRepository
import woowacourse.movie.local.source.ReservationTicketDatabase
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import woowacourse.movie.ui.seat.adapter.OnSeatSelectedListener
import woowacourse.movie.ui.seat.adapter.SeatsAdapter

class SeatReservationActivity : AppCompatActivity(), SeatReservationContract.View {
    private val binding: ActivitySeatReservationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_seat_reservation,
        )
    }

    private lateinit var presenter: SeatReservationContract.Presenter
    private lateinit var onReserveButtonClickedListener: OnReserveClickedListener

    private lateinit var seatsAdapter: SeatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()

        with(presenter) {
            loadAllSeats()
            loadTimeReservation()
        }
        onReserveButtonClickedListener = OnReserveClickedListener { presenter.attemptReserve() }
        binding.onReserveClickedListener = onReserveButtonClickedListener
    }

    private fun initPresenter() {
        val theaterId = intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)
        val timeReservationId = intent.getIntExtra(TIME_RESERVATION_ID, DEFAULT_TIME_RESERVATION_ID)
        presenter =
            SeatReservationPresenter(
                this,
                DummyScreenDataSource(DummySeatsDataSource()),
                OfflineReservationRepository(
                    ReservationTicketDatabase.getDatabase(applicationContext).reservationDao(),
                ),
                DummyTheatersDataSource(),
                theaterId,
                timeReservationId,
            )
    }

    override fun showTimeReservation(timeReservation: TimeReservation) {
        binding.timeReservation = timeReservation
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
    }

    override fun showAllSeats(seats: Seats) {
        val seatsGridLayout = binding.rvSeatReservationSeats
        seatsGridLayout.layoutManager = GridLayoutManager(this, seats.maxColumn())

        seatsAdapter =
            SeatsAdapter(
                object : OnSeatSelectedListener {
                    override fun onSeatSelection(
                        seat: Seat,
                        selection: Boolean,
                    ) {
                        presenter.selectSeat(seat.position, selection)
                    }
                },
            )

        seatsGridLayout.adapter = seatsAdapter

        seatsAdapter.submitList(seats.seats)
    }

    override fun showSeats(seats: Seats) {
        seatsAdapter.submitList(seats.seats)
        presenter.calculateTotalPrice()
    }

    override fun activateReservation(activated: Boolean) {
        with(binding.btnSeatReservationComplete) {
            if (activated) {
                isEnabled = true
                setBackgroundColor(getColor(R.color.complete_activated))
            } else {
                isEnabled = false
                setBackgroundColor(getColor(R.color.complete_deactivated))
            }
        }
    }

    override fun checkReservationConfirm() {
        val alertDialog =
            AlertDialog.Builder(this)
                .setTitle(R.string.check_reservation_title)
                .setMessage(R.string.check_reservation_content)
                .setPositiveButton(R.string.check_reservation_complete) { _, _ ->
                    presenter.reserve()
                }
                .setNegativeButton(R.string.check_reservation_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()

        alertDialog.show()
    }

    override fun showCompleteReservation(reservationTicketId: Int) {
        ReservationCompleteActivity.startActivity(this, reservationTicketId)
    }

    override fun showSeatReservationFail(throwable: Throwable) {
        showToast(throwable)
    }

    override fun showSelectedSeatFail(throwable: Throwable) {
        showToast(throwable)
    }

    private fun showToast(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
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

fun interface OnReserveClickedListener {
    fun onClick()
}
