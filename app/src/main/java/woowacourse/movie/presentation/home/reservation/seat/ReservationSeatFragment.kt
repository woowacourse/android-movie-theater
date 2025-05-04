package woowacourse.movie.presentation.home.reservation.seat

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.custom.CustomAlertDialog
import woowacourse.movie.presentation.common.custom.DialogInfo
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.TicketUiModel
import woowacourse.movie.presentation.home.reservation.result.ReservationResultFragment

class ReservationSeatFragment :
    BaseFragment<FragmentReservationSeatBinding>(R.layout.fragment_reservation_seat),
    ReservationSeatContract.View {
    private lateinit var presenter: ReservationSeatPresenter
    private lateinit var views: ReservationSeatViews
    private lateinit var publishDialogInfo: DialogInfo
    private val dialog: CustomAlertDialog by lazy { CustomAlertDialog(requireContext()) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initPresenterAndViews()
        setupDialogInfo()
        setupInitialData(savedInstanceState)
    }

    private fun initPresenterAndViews() {
        presenter = ReservationSeatPresenter(this)
        views = ReservationSeatViews(requireContext(), binding)
    }

    private fun setupDialogInfo() {
        publishDialogInfo =
            DialogInfo(
                title = getString(R.string.reservation_dialog_title),
                message = getString(R.string.reservation_dialog_message),
                positiveButtonText = getString(R.string.reservation_dialog_positive),
                negativeButtonText = getString(R.string.reservation_dialog_negative),
                onClickPositiveButton = { presenter.publishTickets() },
                onClickNegativeButton = { it.dismiss() },
            )
    }

    private fun setupInitialData(savedInstanceState: Bundle?) {
        val screen = arguments.getParcelableCompat<ScreenUiModel>(BUNDLE_KEY_SCREEN)
        val reservationInfo =
            arguments.getParcelableCompat<ReservationInfoUiModel>(BUNDLE_KEY_RESERVATION_INFO)
        val restoredSeats =
            savedInstanceState?.getParcelableCompat<ScreenUiModel>(BUNDLE_RESTORE_KEY_SEATS)

        presenter.fetchData(reservationInfo, screen, restoredSeats)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedSeats = views.findSelectedViews()
        outState.putParcelable(BUNDLE_RESTORE_KEY_SEATS, ScreenUiModel(selectedSeats))
    }

    override fun showScreen(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel,
        selectedSeats: List<SeatUiModel>,
    ) {
        binding.reservationInfo = reservationInfo
        binding.btnConfirm.setOnClickListener { dialog.show(publishDialogInfo) }
        views.setData(screen, selectedSeats)
        views.setSeatListeners { seat -> presenter.updateSeat(seat) }
    }

    override fun updateSeatState(selectedSeat: SeatUiModel) {
        views.updateSeatState(selectedSeat)
    }

    override fun updateTotalPrice(totalPrice: Int) {
        binding.money = totalPrice
    }

    override fun notifyCanPublish(canPublish: Boolean) {
        binding.canPublish = canPublish
    }

    override fun notifyPublishedTickets(ticket: TicketUiModel) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, ReservationResultFragment.newInstance(ticket))
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
        ) = ReservationSeatFragment().apply {
            arguments =
                bundleOf(
                    BUNDLE_KEY_SCREEN to screen,
                    BUNDLE_KEY_RESERVATION_INFO to reservationInfo,
                )
        }
    }
}
