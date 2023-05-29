package woowacourse.movie.ui.seat

import android.view.View
import android.widget.TableRow
import woowacourse.movie.data.alarm.AlarmStateRepository
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.domain.seat.SelectedSeats
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toModel
import woowacourse.movie.uimodel.MovieTicketInfoModel
import woowacourse.movie.uimodel.MovieTicketModel
import woowacourse.movie.uimodel.SeatModel
import woowacourse.movie.uimodel.SelectedSeatsModel

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val alarmStateRepository: AlarmStateRepository,
    private val reservationRepository: ReservationRepository,
    private val movieTicketInfoModel: MovieTicketInfoModel,
) : SeatSelectionContract.Presenter {
    private var selectedSeats = SelectedSeats()
    val selectedSeatsModel: SelectedSeatsModel
        get() = selectedSeats.toModel()

    init {
        view.initMovieTitleView(movieTicketInfoModel.title)
        updateView(0)
    }

    override fun updateSelectedSeatsModel(selectedSeatsModel: SelectedSeatsModel) {
        selectedSeats = selectedSeatsModel.toDomain()
        updateView(selectedSeats.getAllPrice(movieTicketInfoModel.time))
    }

    override fun addSeat(tableRow: TableRow, row: Int, column: Int) {
        val seat = SeatModel(row, column)
        view.initSeat(tableRow, seat, selectedSeats.contains(seat.toDomain()))
    }

    override fun clickSeat(seatView: View, isSelected: Boolean) {
        if (!isSelected && isSelectionDone()) {
            return
        }
        view.selectSeat(seatView)
    }

    override fun updateSeats(seat: SeatModel, isSelected: Boolean) {
        if (!isSelected && isSelectionDone()) {
            view.showErrorMessage()
            return
        }

        selectedSeats = when (!isSelected) {
            true -> {
                selectedSeats.add(seat.toDomain())
            }
            false -> {
                selectedSeats.delete(seat.toDomain())
            }
        }

        updateView(selectedSeats.getAllPrice(movieTicketInfoModel.time))
    }

    private fun updateView(price: Int) {
        view.updatePriceText(price)
        view.updateButtonEnablement(isSelectionDone())
    }

    override fun makeTicket() {
        MovieTicketModel(
            movieTicketInfo = movieTicketInfoModel,
            seats = selectedSeatsModel,
        ).apply {
            addReservation(this)
            makeAlarm(this)
            view.moveToTicketActivity(this)
        }
    }

    private fun addReservation(ticket: MovieTicketModel) {
        reservationRepository.saveData(ticket)
    }

    private fun makeAlarm(ticket: MovieTicketModel) {
        val isAlarmSwitchOn = alarmStateRepository.getData()
        if (isAlarmSwitchOn) {
            view.makeAlarm(ticket)
        }
    }

    private fun isSelectionDone(): Boolean {
        return selectedSeats.isSelectionDone(movieTicketInfoModel.peopleCount.count)
    }
}
