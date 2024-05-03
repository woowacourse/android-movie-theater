package woowacourse.movie.presentation.ui.reservation

import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.ui.reservation.ReservationContract.View

class ReservationActivity : BaseActivity<ActivityReservationBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_reservation
    override val presenter: ReservationPresenter by lazy {
        ReservationPresenter(
            this,
            DummyReservation,
            DummyScreens(),
        )
    }

    override fun initStartView() {
        val id = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_RESERVATION_ID)
        presenter.loadReservation(id)
    }

    override fun showReservation(
        reservation: Reservation,
        theaterName: String,
    ) {
        binding.reservation = reservation
        binding.theaterName = theaterName
    }

    override fun back() = finish()

    companion object {
        private const val PUT_EXTRA_KEY_RESERVATION_ID = "reservationId"
        private const val DEFAULT_RESERVATION_ID = -1

        fun startActivity(
            context: Context,
            reservationId: Int,
        ) {
            val intent = Intent(context, ReservationActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
            context.startActivity(intent)
        }
    }
}
