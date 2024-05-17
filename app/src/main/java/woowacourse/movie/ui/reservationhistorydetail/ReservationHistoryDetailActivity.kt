package woowacourse.movie.ui.reservationhistorydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationHistoryDetailBinding
import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase

class ReservationHistoryDetailActivity :
    AppCompatActivity(),
    ReservationHistoryDetailContract.View {
    private val binding: ActivityReservationHistoryDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_history_detail)
    }
    private val presenter: ReservationHistoryDetailPresenter by lazy {
        val db = ReservationHistoryDatabase.getInstance(this)
        ReservationHistoryDetailPresenter(this, db)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        val reservationHistoryId =
            intent.getLongExtra(
                RESERVATION_HISTORY_ID,
                DEFAULT_RESERVATION_HISTORY_ID,
            )

        presenter.loadReservation(reservationHistoryId)
    }

    override fun showReservation(reservationHistory: ReservationHistory) {
        runOnUiThread {
            binding.reservationHistory = reservationHistory
        }
    }

    companion object {
        private const val RESERVATION_HISTORY_ID = "reservationHistoryId"
        private const val DEFAULT_RESERVATION_HISTORY_ID = -1L

        fun newIntent(
            context: Context,
            reservationHistoryId: Long,
        ): Intent {
            return Intent(context, ReservationHistoryDetailActivity::class.java).apply {
                putExtra(RESERVATION_HISTORY_ID, reservationHistoryId)
            }
        }
    }
}
