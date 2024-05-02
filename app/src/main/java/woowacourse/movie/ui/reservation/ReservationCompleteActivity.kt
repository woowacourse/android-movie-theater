package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.ui.Currency
import java.util.Locale

class ReservationCompleteActivity : AppCompatActivity(), ReservationContract.View {
    private val presenter: ReservationContract.Presenter by lazy { ReservationPresenter(this, DummyReservation, DummyTheaters()) }
    private val binding: ActivityReservationCompleteBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        val reservationId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_RESERVATION_ID)
        val theaterId =
            intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)
        presenter.loadReservation(reservationId, theaterId)
    }

    override fun showReservation(
        reservation: Reservation,
        theaterName: String,
    ) {
        binding.reservation = reservation
        binding.theaterName = theaterName
    }

    private fun Reservation.currency(): String =
        getString(R.string.reserve_amount, Currency.of(Locale.getDefault().country).format(seats.totalPrice()))

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
        private const val PUT_EXTRA_KEY_RESERVATION_ID = "reservationId"
        private const val DEFAULT_RESERVATION_ID = -1
        private const val DEFAULT_THEATER_ID = -1
        private const val PUT_EXTRA_THEATER_ID_KEY = "theaterId"

        fun startActivity(
            context: Context,
            reservationId: Int,
            theaterId: Int,
        ) {
            val intent = Intent(context, ReservationCompleteActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
            intent.putExtra(PUT_EXTRA_THEATER_ID_KEY, theaterId)
            context.startActivity(intent)
        }
    }
}
