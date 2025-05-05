package woowacourse.movie.view.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.extension.getParcelableCompat

class ReservationResultActivity :
    BaseActivity<ActivityReservationResultBinding>(R.layout.activity_reservation_result),
    ReservationResultContract.View {
    val presenter = ReservationResultPresenter(this)
    lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ticket = intent.getParcelableCompat<Ticket>(BUNDLE_KEY_RESERVATION_INFO)
        presenter.loadReservationInfo(ticket)
    }

    override fun showReservationResult(ticket: Ticket) {
        binding.ticket = ticket
        setupCancelDescription()
    }

    private fun setupCancelDescription() {
        binding.tvCancelDescription.text =
            getString(R.string.reservation_result_cancel_time_description, CANCELLATION_TIME)
    }

    companion object {
        private const val CANCELLATION_TIME = 15
        private const val BUNDLE_KEY_RESERVATION_INFO = "reservation_info"

        fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent =
            Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(
                    BUNDLE_KEY_RESERVATION_INFO,
                    ticket,
                )
            }
    }
}
