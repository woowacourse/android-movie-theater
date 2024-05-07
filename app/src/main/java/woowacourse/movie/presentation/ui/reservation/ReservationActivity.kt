package woowacourse.movie.presentation.ui.reservation

import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.domain.db.AppDatabase
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepositoryImpl
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.ui.reservation.ReservationContract.View

class ReservationActivity : BaseActivity<ActivityReservationBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_reservation

    private lateinit var reservationDao: ReservationDao
    private lateinit var reservationRepository: ReservationRepositoryImpl

    val presenter: ReservationPresenter by lazy {
        ReservationPresenter(
            this,
            reservationRepository,
        )
    }

    override fun initStartView() {
        reservationDao = AppDatabase.getDatabase(applicationContext)!!.reservationDao()
        reservationRepository = ReservationRepositoryImpl(reservationDao)
        val id = intent.getLongExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_RESERVATION_ID)
        presenter.loadReservation(id)
    }

    override fun showReservation(reservation: Reservation) {
        binding.reservation = reservation
    }

    override fun terminateOnError(e: Throwable) {
        showToastMessage(e)
        finish()
    }

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
