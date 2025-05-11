package woowacourse.movie.presentation.view.reservation.seat

import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.presentation.alarm.AlarmScheduler
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toDomain
import woowacourse.movie.presentation.model.toPresentation
import kotlin.concurrent.thread

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
    private val provider: ReservationRepository,
    private val alarmScheduler: AlarmScheduler,
) : SeatSelectContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo
    private lateinit var theaterName: String
    private var selectedSeats = Seats.create()

    override fun fetchData(reservationInfo: ReservationInfoUiModel?) {
        if (reservationInfo == null) {
            view.showErrorDialog()
            return
        }

        this.reservationInfo = reservationInfo.toDomain()
        this.theaterName = reservationInfo.theaterName

        view.showReservationInfo(
            reservationInfo.title,
            DEFAULT_PRICE,
        )
    }

    override fun seatSelect(seatId: String) {
        if (selectedSeats.size == reservationInfo.count.value && !selectedSeats.contains(seatId)) {
            view.showSeatCountError(reservationInfo.count.value)
            return
        }

        val isSelected = selectedSeats.click(seatId)
        if (isSelected) {
            view.showSelectedSeat(seatId)
        } else {
            view.showDeselectedSeat(seatId)
        }

        view.showTotalPrice(selectedSeats.totalPrice)
        view.updateConfirmButtonEnabled(selectedSeats.size == reservationInfo.count.value)
    }

    override fun confirmRequested(
        title: String,
        message: String,
    ) {
        view.showReservationDialog(title, message)
    }

    override fun reservationConfirmed() {
        val reservationInfoUiModel = createReservationInfo()

        if (!alarmScheduler.canScheduleAlarm()) {
            view.showExactAlarmSettingDialog(reservationInfoUiModel)
            return
        }

        alarmScheduler.scheduleAlarm(reservationInfoUiModel)

        thread {
            reservationInfo = reservationInfoUiModel.toDomain()
            provider.saveReservation(reservationInfo)
            view.navigateToComplete(reservationInfoUiModel)
        }
    }

    override fun getSelectedSeatIds(): List<String> = selectedSeats.labels()

    override fun restoreSelectedSeats(seatIds: List<String>) {
        for (seatId in seatIds) {
            selectedSeats.add(seatId)
            view.showSelectedSeat(seatId)
        }
        view.showTotalPrice(selectedSeats.totalPrice)
        view.updateConfirmButtonEnabled(selectedSeats.size == reservationInfo.count.value)
    }

    override fun restoreButtonState() {
        val isEnabled = selectedSeats.size == reservationInfo.count.value
        view.updateConfirmButtonEnabled(isEnabled)
    }

    private fun createReservationInfo(): ReservationInfoUiModel =
        ReservationInfoUiModel(
            title = reservationInfo.title,
            dateTime = reservationInfo.dateTime,
            count = reservationInfo.count.value,
            seats = selectedSeats.toPresentation(),
            theaterName = theaterName,
        )

    companion object {
        private const val DEFAULT_PRICE = 0
    }
}
