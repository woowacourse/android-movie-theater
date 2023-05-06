package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.ReservationAgency
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.SeatRepository
import woowacourse.movie.view.mapper.toDomainModel
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.SeatUiModel

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val reservationRepository: ReservationRepository,
    private val seatRepository: SeatRepository
) : SeatSelectionContract.Presenter {

    private val reservationOptions: ReservationOptions? by lazy {
        view.getReservationOptions()
    }
    private lateinit var reservationAgency: ReservationAgency
    private var selectedSeatCount = 0
    private var selectedSeats: List<Seat> = emptyList()

    override fun setUp() {
        view.initSeatButtons(Seat.MIN_ROW..Seat.MAX_ROW, Seat.MIN_COLUMN..Seat.MAX_COLUMN)
        reservationOptions?.let {
            view.initReserveLayout(it)
        }
        initReservationAgency()
    }

    override fun createSeat(row: Int, col: Int): SeatUiModel {
        return Seat(col, row).toUiModel()
    }

    override fun selectSeat(): Boolean {
        reservationOptions?.let {
            if (selectedSeatCount < it.peopleCount) {
                selectedSeatCount++
                if (selectedSeatCount == it.peopleCount) {
                    onSelectionDone()
                }
                return true
            }
        }
        return false
    }

    private fun onSelectionDone() {
        val selectedSeatsIndex = view.findSelectedSeatsIndex()
        selectedSeats = selectedSeatsIndex.map { index ->
            Seat(
                index % Seat.MAX_COLUMN + 1,
                index / Seat.MAX_COLUMN + 1,
            )
        }

        if (reservationAgency.canReserve(selectedSeats)) {
            val reservationFee = reservationAgency.calculateReservationFee(selectedSeats)
            view.enableReservation(reservationFee.amount)
        }
    }

    override fun deselectSeat() {
        selectedSeatCount--
        view.disableReservation()
    }

    private fun initReservationAgency() {
        val movie = view.getMovie()?.toDomainModel()

        if (movie != null && reservationOptions != null) {
            reservationAgency = ReservationAgency(
                movie,
                reservationOptions!!.peopleCount,
                reservationOptions!!.screeningDateTime,
            )
        }
    }

    override fun reserveSeats() {
        val reservation = reservationAgency.reserve(selectedSeats)
        reservation?.let {
            val reservationId = reservationRepository.add(reservation)
            seatRepository.addSeats(reservation.seats, reservationId)
            view.registerReservationAlarm(reservation.toUiModel())
        }
    }
}
