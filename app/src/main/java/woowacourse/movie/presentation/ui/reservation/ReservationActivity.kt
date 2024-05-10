package woowacourse.movie.presentation.ui.reservation

import android.content.Context
import android.content.Intent
import woowacourse.movie.MovieApplication.Companion.db
import woowacourse.movie.R
import woowacourse.movie.data.repository.local.ReservationRepositoryImpl
import woowacourse.movie.data.repository.remote.DummyTheater
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.presentation.base.BaseMvpBindingActivity
import woowacourse.movie.presentation.ui.reservation.ReservationContract.View

class ReservationActivity : BaseMvpBindingActivity<ActivityReservationBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_reservation
    val repository: ReservationRepository by lazy { ReservationRepositoryImpl(db.dao()) }
    override val presenter: ReservationPresenter by lazy {
        ReservationPresenter(this, repository, DummyTheater)
    }

    override fun initStartView() {
        val id = intent.getLongExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_RESERVATION_ID)
        presenter.loadReservation(id)
    }

    override fun showReservation(
        reservation: Reservation,
        theaterName: String,
    ) {
        runOnUiThread {
            binding.reservation = reservation
            binding.theaterName = theaterName
        }
    }

    override fun navigateBackToPrevious() = runOnUiThread { finish() }

    companion object {
        private const val PUT_EXTRA_KEY_RESERVATION_ID = "reservationId"
        private const val DEFAULT_RESERVATION_ID = -1L

        fun startActivity(
            context: Context,
            reservationId: Long,
        ) {
            val intent = Intent(context, ReservationActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
            context.startActivity(intent)
        }
    }
}
