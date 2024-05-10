package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.util.BindingActivity
import java.time.LocalDate
import java.time.LocalTime

class ReservationCompleteActivity :
    BindingActivity<ActivityReservationCompleteBinding>(R.layout.activity_reservation_complete),
    ReservationContract.View {
    private val presenter: ReservationContract.Presenter by lazy {
        ReservationPresenter(
            this,
            DummyReservation,
            DummyTheaters(),
            ReservationHistoryDatabase.getInstance(this),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        val reservationId = intent.getIntExtra(RESERVATION_ID, DEFAULT_RESERVATION_ID)
        val theaterId = intent.getIntExtra(THEATER_ID, DEFAULT_THEATER_ID)

        presenter.loadReservation(reservationId, theaterId)
    }

    override fun showReservation(
        reservation: Reservation,
        theaterName: String,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
    ) {
        binding.reservation = reservation
        binding.theaterName = theaterName
        binding.screeningDate = screeningDate
        binding.screeningTime = screeningTime
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun goToBack(message: String) {
        showToastMessage(message)
        finish()
    }

    override fun unexpectedFinish(message: String) {
        showSnackBar(message)
        finish()
    }

    companion object {
        private const val RESERVATION_ID = "reservationId"
        private const val THEATER_ID = "theaterId"
        private const val DEFAULT_RESERVATION_ID = -1
        private const val DEFAULT_THEATER_ID = -1

        fun startActivity(
            context: Context,
            reservationId: Int,
            theaterId: Int,
        ) {
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(RESERVATION_ID, reservationId)
                putExtra(THEATER_ID, theaterId)
                context.startActivity(this)
            }
        }
    }
}
