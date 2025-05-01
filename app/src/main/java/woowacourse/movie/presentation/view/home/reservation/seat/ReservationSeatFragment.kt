package woowacourse.movie.presentation.view.home.reservation.seat

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.SeatUiModel
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.util.DialogInfo
import woowacourse.movie.presentation.view.home.reservation.result.ReservationResultFragment

class ReservationSeatFragment :
    BaseFragment<FragmentReservationSeatBinding>(R.layout.fragment_reservation_seat),
    ReservationSeatContract.View {
    private val presenter: ReservationSeatPresenter by lazy { ReservationSeatPresenter(this) }
    private val views: ReservationSeatViews by lazy {
        ReservationSeatViews(
            requireContext(),
            binding,
        )
    }

    private val publishTicketConfirmationDialogInfo: DialogInfo by lazy {
        DialogInfo(
            title = getString(R.string.reservation_dialog_title),
            message = getString(R.string.reservation_dialog_message),
            positiveButtonText = getString(R.string.reservation_dialog_positive),
            negativeButtonText = getString(R.string.reservation_dialog_negative),
            onClickPositiveButton = { presenter.publishTickets() },
            onClickNegativeButton = { it.dismiss() },
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val screen = arguments?.getParcelableCompat<ScreenUiModel>(BUNDLE_KEY_SCREEN)
        val reservationInfo =
            arguments.getParcelableCompat<ReservationInfoUiModel>(BUNDLE_KEY_RESERVATION_INFO)
        val restoredSeats =
            savedInstanceState?.getParcelableCompat<ScreenUiModel>(BUNDLE_RESTORE_KEY_SEATS)
        binding.reservationInfo = reservationInfo
        presenter.fetchData(reservationInfo, screen, restoredSeats)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(BUNDLE_RESTORE_KEY_SEATS, ScreenUiModel(views.findSelectedViews()))
    }

    override fun showScreen(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel,
        selectedSeats: List<SeatUiModel>,
        totalPrice: Int,
        canPublish: Boolean,
    ) {
        views.setData(reservationInfo, screen, selectedSeats)
        views.setEventListeners(
            { views.dialog.show(publishTicketConfirmationDialogInfo) },
            { seat -> presenter.updateSeat(seat) },
        )

        views.updateConfirmButton(canPublish)
        views.updateTotalPrice(totalPrice)
    }

    override fun updateSeatState(
        selectedSeat: SeatUiModel,
        totalPrice: Int,
        canPublish: Boolean,
    ) {
        views.updateSeatState(selectedSeat)
        views.updateTotalPrice(totalPrice)
        views.updateConfirmButton(canPublish)
    }

    override fun notifyPublishedTickets(ticketBundle: TicketBundleUiModel) {
        val fragment = ReservationResultFragment.newInstance(ticketBundle)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    override fun notifySeatUpdateFailed(message: String) {
        showToast(message.ifEmpty { getString(R.string.default_error_message) })
    }

    companion object {
        private const val BUNDLE_KEY_RESERVATION_INFO = "reservation_info"
        private const val BUNDLE_KEY_SCREEN = "screen"
        private const val BUNDLE_RESTORE_KEY_SEATS = "seats"

        fun newInstance(
            reservationInfo: ReservationInfoUiModel,
            screen: ScreenUiModel,
        ): ReservationSeatFragment =
            ReservationSeatFragment().apply {
                arguments =
                    bundleOf(
                        BUNDLE_KEY_SCREEN to screen,
                        BUNDLE_KEY_RESERVATION_INFO to reservationInfo,
                    )
            }
    }
}
