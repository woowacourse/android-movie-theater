package woowacourse.movie.presentation.view.home.reservation.seat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationSeatBinding
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import woowacourse.movie.presentation.AlarmScheduler
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.SeatUiModel
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.model.toUiModel
import woowacourse.movie.presentation.util.CustomAlertDialog
import woowacourse.movie.presentation.util.DialogInfo
import woowacourse.movie.presentation.view.home.reservation.result.ReservationResultFragment
import java.time.LocalDateTime

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
        views = ReservationSeatViews(requireContext(), binding, SeatViewFactory(requireContext()))
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

    override fun notifyTotalPrice(totalPrice: Int) {
        binding.money = totalPrice
    }

    override fun notifyCanPublish(canPublish: Boolean) {
        binding.canPublish = canPublish
    }

    override fun notifyPublishedTickets(ticketBundle: TicketBundleUiModel) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, ReservationResultFragment.newInstance(ticketBundle))
            addToBackStack(null)
        }
    }

    override fun notifySeatUpdateFailed(message: String) {
        showToast(message.ifEmpty { getString(R.string.default_error_message) })
    }

    override fun savePublishedTickets(ticketBundle: TicketBundle) {
        val context = requireContext()
        val dao = ReservationDatabase.getInstance(context).reservationDao()
        val repository = ReservationRepository(dao)
        val alarmScheduler = AlarmScheduler(context)

        Thread {
            repository.saveReservation(ticketBundle)
            val alarmTime = ticketBundle.dateTime.minusMinutes(30)
            if (alarmTime.isAfter(LocalDateTime.now()) &&
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
            ) {
                alarmScheduler.schedule(alarmTime, ticketBundle.toUiModel())
            }
        }.start()
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
