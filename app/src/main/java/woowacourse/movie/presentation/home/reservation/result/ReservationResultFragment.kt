package woowacourse.movie.presentation.home.reservation.result

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationResultBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.base.HomeButtonHandler
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.TicketUiModel

class ReservationResultFragment :
    BaseFragment<FragmentReservationResultBinding>(R.layout.fragment_reservation_result),
    ReservationResultContract.View,
    HomeButtonHandler {
    private val presenter: ReservationResultPresenter by lazy { ReservationResultPresenter(this) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val ticket =
            arguments.getParcelableCompat<TicketUiModel>(BUNDLE_KEY_TICKET)
        presenter.fetchData(ticket)
    }

    override fun showScreen(
        ticket: TicketUiModel,
        cancellationTime: Int,
    ) {
        binding.ticket = ticket
        binding.cancellationTime = cancellationTime
    }

    companion object {
        private const val BUNDLE_KEY_TICKET = "ticket"

        fun newInstance(ticket: TicketUiModel): ReservationResultFragment =
            ReservationResultFragment().apply {
                arguments = bundleOf(BUNDLE_KEY_TICKET to ticket)
            }
    }
}
