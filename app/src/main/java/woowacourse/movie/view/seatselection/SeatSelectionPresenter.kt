package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.price.PriceCalculator
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.system.PriceSystem
import woowacourse.movie.domain.system.Seat
import woowacourse.movie.domain.system.SeatSelectSystem
import woowacourse.movie.domain.system.SelectResult
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationOptions

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val reserveOption: ReservationOptions,
    private val reservationRepository: ReservationRepository,
    theaterRepository: TheaterRepository,
) : SeatSelectionContract.Presenter {
    private var price = Price(0)
    private val theater = theaterRepository.findTheater(reserveOption.theaterName)
    private val seatSelectSystem: SeatSelectSystem =
        SeatSelectSystem(theater.seatInfo, reserveOption.peopleCount)
    private val priceSystem: PriceSystem =
        PriceSystem(PriceCalculator(theater.discountPolicies), reserveOption.screeningDateTime)

    override fun fetchSeatsData(colorOfGrade: Map<Grade, Int>) {
        val seatUiInfo = theater.seatInfo.toUiModel(colorOfGrade)
        for (row in 0 until seatUiInfo.maxRow) {
            view.createRow(seatUiInfo)
            for (col in 0 until seatUiInfo.maxCol) {
                val seat = Seat(row, col).toUiModel(seatUiInfo.colorOfRow[row] ?: 0)
                view.createSeat(seat)
            }
        }
    }

    override fun updateSeat(row: Int, col: Int) {
        val result = seatSelectSystem.select(row, col)
        val index = rowColToIndex(row, col)
        when (result) {
            is SelectResult.Success.Selection -> {
                view.onSeatSelectedByIndex(index, result.isSelectAll)
            }
            is SelectResult.Success.Deselection -> {
                view.onSeatDeselectedByIndex(index)
            }
            is SelectResult.MaxSelection -> {
                view.showSeatMaxSelectionToast()
            }
            is SelectResult.WrongInput -> {
                view.showWrongInputToast()
            }
        }
        price = priceSystem.getCurrentPrice(price, result)
        view.setPrice(price.toUiModel())
    }

    override fun reserve() {
        val reservation = Reservation(
            reserveOption.title,
            reserveOption.screeningDateTime,
            seatSelectSystem.seats,
            price,
            theater.name,
        )
        reservationRepository.add(reservation)
        view.onReserveClick(reservation.toUiModel())
    }

    private fun rowColToIndex(row: Int, col: Int): Int = row * theater.seatInfo.size.col + col
}
