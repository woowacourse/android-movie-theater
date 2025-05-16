package woowacourse.movie.presentation.view.reservation.seat

import android.os.Bundle
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toDomain
import woowacourse.movie.presentation.model.toPresentation

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
    private val reservationRepository: ReservationRepository,
) : SeatSelectContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo
    private lateinit var theaterName: String

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
        if (reservationInfo.seats.size == reservationInfo.count.value &&
            !reservationInfo.seats.contains(
                seatId,
            )
        ) {
            view.showSeatCountError(reservationInfo.count.value)
            return
        }

        val isSelected = reservationInfo.seats.click(seatId)
        if (isSelected) {
            view.showSelectedSeat(seatId)
        } else {
            view.showDeselectedSeat(seatId)
        }

        view.showTotalPrice(reservationInfo.seats.totalPrice)
        view.updateConfirmButtonEnabled(reservationInfo.seats.size == reservationInfo.count.value)
    }

    override fun confirmRequested(
        title: String,
        message: String,
    ) {
        view.showReservationDialog(title, message)
    }

    override fun reservationConfirmed() {
        val reservationInfoUiModel = createReservationInfo()
        view.navigateCompleteWithAlarmCheck(reservationInfoUiModel)
    }

    override fun saveSelectedSeats(outState: Bundle) {
        outState.putStringArrayList(
            Extras.SeatsData.SEATS_KEY,
            ArrayList(reservationInfo.seats.labels()),
        )
    }

    override fun saveReservation(reservationInfo: ReservationInfoUiModel) {
        reservationRepository.saveReservation(reservationInfo.toDomain())
        view.navigateToComplete(reservationInfo)
    }

    override fun restoreSelectedSeats(seatIds: List<String>) {
        for (seatId in seatIds) {
            reservationInfo.seats.add(seatId)
            view.showSelectedSeat(seatId)
        }
        view.showTotalPrice(reservationInfo.seats.totalPrice)
        view.updateConfirmButtonEnabled(reservationInfo.seats.size == reservationInfo.count.value)
    }

    override fun restoreButtonState() {
        val isEnabled = reservationInfo.seats.size == reservationInfo.count.value
        view.updateConfirmButtonEnabled(isEnabled)
    }

    private fun createReservationInfo(): ReservationInfoUiModel =
        ReservationInfoUiModel(
            title = reservationInfo.title,
            dateTime = reservationInfo.dateTime,
            count = reservationInfo.count.value,
            seats = reservationInfo.seats.toPresentation(),
            theaterName = theaterName,
        )

    companion object {
        private const val DEFAULT_PRICE = 0
    }
}
