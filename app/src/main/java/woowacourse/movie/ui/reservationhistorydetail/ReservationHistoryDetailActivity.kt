package woowacourse.movie.ui.reservationhistorydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.db.ReservationHistoryDatabase
import woowacourse.movie.domain.model.Reservation
import java.time.LocalDate
import java.time.LocalTime

class ReservationHistoryDetailActivity : AppCompatActivity(), ReservationHistoryDetailContract.View {
    private val binding: ActivityReservationCompleteBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
    }
    private lateinit var presenter: ReservationHistoryDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        initView()
    }

    private fun initPresenter() {
        val db = ReservationHistoryDatabase.getInstance(this)

        presenter = ReservationHistoryDetailPresenter(this, db)
    }

    private fun initView() {
        val reservationHistoryId =
            intent.getLongExtra(
                RESERVATION_HISTORY_ID,
                DEFAULT_RESERVATION_HISTORY_ID,
            )

        presenter.loadReservation(reservationHistoryId)
    }

    override fun showReservation(
        reservation: Reservation,
        theaterName: String,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
    ) {
        runOnUiThread {
            binding.reservation = reservation
            binding.theaterName = theaterName
            binding.screeningDate = screeningDate
            binding.screeningTime = screeningTime
        }
    }

    companion object {
        private const val RESERVATION_HISTORY_ID = "reservationHistoryId"
        private const val DEFAULT_RESERVATION_HISTORY_ID = -1L

        fun startActivity(
            context: Context,
            reservationHistoryId: Long,
        ) {
            val intent = Intent(context, ReservationHistoryDetailActivity::class.java)
            intent.putExtra(RESERVATION_HISTORY_ID, reservationHistoryId)
            context.startActivity(intent)
        }
    }
}
