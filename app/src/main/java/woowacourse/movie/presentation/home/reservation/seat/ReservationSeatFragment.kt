package woowacourse.movie.presentation.home.reservation.seat

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.db.ReservationRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.presentation.alarm.AlarmHelper
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.custom.CustomAlertDialog
import woowacourse.movie.presentation.common.custom.DialogInfo
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.TicketUiModel
import woowacourse.movie.presentation.home.reservation.result.ReservationResultActivity

class ReservationSeatFragment :
    BaseFragment<FragmentReservationSeatBinding>(R.layout.fragment_reservation_seat),
    ReservationSeatContract.View {
    private lateinit var presenter: ReservationSeatPresenter
    private lateinit var views: ReservationSeatViews
    private val dialog: CustomAlertDialog by lazy { CustomAlertDialog(requireContext()) }
    private val publishDialogInfo: DialogInfo by lazy {
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
        initPresenter()
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
        binding.btnConfirm.setOnClickListener { dialog.show(publishDialogInfo) }
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

    override fun notifyPublishedTickets(ticket: TicketUiModel) {
        if (!AlarmHelper.canScheduleExactAlarms(requireContext())) {
            showExactAlarmPermissionDialog(ticket)
            return
        }

        navigateToResultScreen(ticket)
    }

    private fun initPresenter() {
        val dao = ReservationDatabase.getInstance(requireContext()).reservationDao()
        val reservationRepository = ReservationRepositoryImpl(dao)
        presenter = ReservationSeatPresenter(this, reservationRepository)
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
        DialogInfo(
            title = getString(R.string.exact_alarm_permission_required_title),
            message = getString(R.string.exact_alarm_permission_required_message),
            positiveButtonText = getString(R.string.exact_alarm_permission_required_positive),
            negativeButtonText = getString(R.string.exact_alarm_permission_required_negative),
            onClickPositiveButton = {
                AlarmHelper.requestExactAlarmPermission(requireContext())
                it.dismiss()
            },
            onClickNegativeButton = {
                showToast(getString(R.string.exact_alarm_permission_not_granted_message))
                navigateToResultScreen(ticket)
                it.dismiss()
            },
        ).also { dialog.show(it) }
    }

    private fun navigateToResultScreen(ticket: TicketUiModel) {
        AlarmHelper.setAlarm(requireContext(), ticket)
        startActivity(ReservationResultActivity.newIntent(requireContext(), ticket))
        requireActivity().finish()
    }

    override fun notifySeatUpdateFailed(message: String) {
        showToast(message.ifEmpty { getString(R.string.default_error_message) })
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
