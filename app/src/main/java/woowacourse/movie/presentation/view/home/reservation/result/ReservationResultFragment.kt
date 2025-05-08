package woowacourse.movie.presentation.view.home.reservation.result

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationResultBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.base.HomeButtonHandler
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.TicketBundleUiModel

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

        val ticketBundle =
            arguments.getParcelableCompat<TicketBundleUiModel>(BUNDLE_KEY_TICKET_BUNDLE)
        presenter.fetchDate(ticketBundle)
    }

    override fun showScreen(
        ticketBundle: TicketBundleUiModel,
        cancellationTime: Int,
    ) {
        binding.ticket = ticketBundle
        binding.cancellationTime = cancellationTime
    }

    companion object {
        private const val BUNDLE_KEY_TICKET_BUNDLE = "ticket_bundle"

        fun newInstance(ticketBundle: TicketBundleUiModel): ReservationResultFragment =
            ReservationResultFragment().apply {
                arguments = bundleOf(BUNDLE_KEY_TICKET_BUNDLE to ticketBundle)
            }
    }
}
