package woowacourse.movie.presentation.home.reservation.seat

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.presentation.alarm.AlarmHelper
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.TicketUiModel
import woowacourse.movie.presentation.home.reservation.result.ReservationResultActivity

class ReservationSeatFragment :
    BaseFragment<FragmentReservationSeatBinding>(R.layout.fragment_reservation_seat),
    ReservationSeatContract.View {
    private val presenter: ReservationSeatPresenter by lazy { ReservationSeatPresenter(this) }
    private lateinit var views: ReservationSeatViews

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        restoreOrInitData(savedInstanceState)
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
        binding.btnConfirm.setOnClickListener { showPublishConfirmDialog() }
        views.setData(screen, selectedSeats)
        views.setSeatListeners { presenter.updateSeat(it) }
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

    override fun notifyPublishedTicketSuccess(ticket: TicketUiModel) =
        requireActivity().runOnUiThread {
            if (!AlarmHelper.canScheduleExactAlarms(requireContext())) {
                showExactAlarmPermissionDialog(ticket)
                return@runOnUiThread
            }

            navigateToResultScreen(ticket)
        }

    private fun initViews() {
        views = ReservationSeatViews(requireContext(), binding)
    }

    private fun restoreOrInitData(savedInstanceState: Bundle?) {
        val screen = arguments.getParcelableCompat<ScreenUiModel>(BUNDLE_KEY_SCREEN)
        val reservationInfo =
            arguments.getParcelableCompat<ReservationInfoUiModel>(BUNDLE_KEY_RESERVATION_INFO)
        val restoredSeats =
            savedInstanceState?.getParcelableCompat<ScreenUiModel>(BUNDLE_RESTORE_KEY_SEATS)
        presenter.fetchData(reservationInfo, screen, restoredSeats)
    }

    private fun showExactAlarmPermissionDialog(ticket: TicketUiModel) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.exact_alarm_permission_required_title)
            .setMessage(R.string.exact_alarm_permission_required_message)
            .setPositiveButton(R.string.exact_alarm_permission_required_positive) { dialog, _ ->
                AlarmHelper.requestExactAlarmPermission(requireContext())
                dialog.dismiss()
            }
            .setNegativeButton(R.string.exact_alarm_permission_required_negative) { dialog, _ ->
                showToast(getString(R.string.exact_alarm_permission_not_granted_message))
                navigateToResultScreen(ticket)
                dialog.dismiss()
            }
            .show()
    }

    override fun notifySeatUpdateFailed(message: String) {
        showFailedToast(message)
    }

    override fun notifyPublishTicketFailed() = requireActivity().runOnUiThread {
        showFailedToast(getString(R.string.publish_ticket_failed_message))
    }

    private fun showFailedToast(message: String) {
        showToast(message.ifEmpty { getString(R.string.default_error_message) })
    }

    private fun navigateToResultScreen(ticket: TicketUiModel) {
        AlarmHelper.setAlarm(requireContext(), ticket)
        startActivity(ReservationResultActivity.newIntent(requireContext(), ticket))
        requireActivity().finish()
    }

    private fun showPublishConfirmDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.reservation_dialog_title)
            .setMessage(R.string.reservation_dialog_message)
            .setCancelable(false)
            .setPositiveButton(R.string.reservation_dialog_positive) { _, _ -> presenter.publishTickets() }
            .setNegativeButton(R.string.reservation_dialog_negative) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    companion object {
        private const val BUNDLE_KEY_RESERVATION_INFO = "reservation_info"
        private const val BUNDLE_KEY_SCREEN = "screen"
        private const val BUNDLE_RESTORE_KEY_SEATS = "seats"

        fun newBundle(
            reservationInfo: ReservationInfoUiModel,
            screen: ScreenUiModel,
        ) = bundleOf(
            BUNDLE_KEY_SCREEN to screen,
            BUNDLE_KEY_RESERVATION_INFO to reservationInfo,
        )
    }
}
