package woowacourse.movie.presentation.home.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationResultBinding
import woowacourse.movie.presentation.common.base.BaseActivity
import woowacourse.movie.presentation.common.base.HomeButtonHandler
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.TicketUiModel

class ReservationResultActivity :
    BaseActivity<FragmentReservationResultBinding>(R.layout.fragment_reservation_result),
    ReservationResultContract.View,
    HomeButtonHandler {
    private val presenter: ReservationResultPresenter by lazy { ReservationResultPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()

        val ticket = intent.getParcelableCompat<TicketUiModel>(BUNDLE_KEY_TICKET)
        presenter.fetchData(ticket)
    }

    override fun showScreen(
        ticket: TicketUiModel,
        cancellationTime: Int,
    ) {
        binding.ticket = ticket
        binding.cancellationTime = cancellationTime
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val BUNDLE_KEY_TICKET = "ticket"

        fun newIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent =
            Intent(context, ReservationResultActivity::class.java).putExtra(
                BUNDLE_KEY_TICKET,
                ticket,
            )
    }
}
