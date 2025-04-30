package woowacourse.movie.presentation.view.home.reservation.result

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationResultBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.base.HomeButtonHandler
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.view.home.movies.MoviesFragment

class ReservationResultFragment :
    BaseFragment<FragmentReservationResultBinding>(R.layout.fragment_reservation_result),
    ReservationResultContract.View,
    HomeButtonHandler {
    private val views: ReservationResultViews by lazy {
        ReservationResultViews(
            requireContext(),
            binding,
        )
    }
    private val presenter: ReservationResultPresenter by lazy { ReservationResultPresenter(this) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setBackPressedDispatcher()

        val ticketBundle =
            arguments.getParcelableCompat<TicketBundleUiModel>(BUNDLE_KEY_TICKET_BUNDLE)
        presenter.fetchDate(ticketBundle)
    }

    override fun showScreen(
        ticketBundle: TicketBundleUiModel,
        cancellationTime: Int,
    ) {
        views.bindReservationResult(ticketBundle, cancellationTime)
    }

    override fun onHomePressed() {
        navigateToMoviesScreen()
    }

    private fun setBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToMoviesScreen()
                }
            },
        )
    }

    private fun navigateToMoviesScreen() {
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, MoviesFragment())
        }
    }

    companion object {
        private const val BUNDLE_KEY_TICKET_BUNDLE = "ticket_bundle"

        fun newInstance(ticketBundle: TicketBundleUiModel): ReservationResultFragment =
            ReservationResultFragment().apply {
                arguments = bundleOf(BUNDLE_KEY_TICKET_BUNDLE to ticketBundle)
            }
    }
}
