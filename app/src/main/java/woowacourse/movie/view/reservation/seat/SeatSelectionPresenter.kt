package woowacourse.movie.view.reservation.seat

import woowacourse.movie.db.ReservationInfoDao
import woowacourse.movie.db.ReservationInfoEntity
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatFactory
import woowacourse.movie.domain.model.TicketMachine
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val dao: ReservationInfoDao,
) : SeatSelectionContract.Presenter {
    var reservationInfo: ReservationInfo? = null
    private val seatFactory = SeatFactory.default()
    private val ticketMachine = TicketMachine()

    override fun loadSeats(reservationInfo: ReservationInfo?) {
        val seats = seatFactory.createSeats()
        this.reservationInfo = reservationInfo

        view.showSeats(seats)
        updateScreen()
    }

    override fun selectSeat(seat: Seat) {
        try {
            reservationInfo = reservationInfo?.updateSeats(seat)
            view.updateSeatSelection(seat, seat.isSelected)
            updateScreen()
        } catch (e: IllegalArgumentException) {
            view.showError(e.message ?: "좌석 선택 오류")
        }
    }

    private fun updateScreen() {
        view.showTotalPrice(reservationInfo?.totalPrice() ?: throw IllegalArgumentException())
        view.enableConfirmButton(canCompleteReservation())
    }

    private fun canCompleteReservation(): Boolean = reservationInfo?.let { ticketMachine.canPublish(it) } ?: false

    override fun showConfirmButton() {
        if (canCompleteReservation()) {
            view.showReservationDialog()
        } else {
            view.showError("좌석을 선택해주세요")
        }
    }

    override fun completeReservation() {
        reservationInfo?.let {
            thread {
                dao.saveReservation(it.toEntity())
            }
            view.navigateToResult(it)
        }
    }

    private fun ReservationInfo.toEntity(): ReservationInfoEntity =
        ReservationInfoEntity(
            this.title,
            this.reservationDateTime,
            this.reservationCount.value,
            this.seats,
            this.cinema.name,
            this.totalPrice(),
        )
}
