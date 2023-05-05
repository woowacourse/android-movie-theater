package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.price.PriceCalculator
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.system.PriceSystem
import woowacourse.movie.domain.system.SeatSelectSystem
import woowacourse.movie.domain.system.SelectResult
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.SeatInfoUiModel

class SeatSelectionPresenter(private val view: SeatSelectionContract.View, private val reserveOption: ReservationOptions, theaterRepository: TheaterRepository) : SeatSelectionContract.Presenter {
    private var price = Price(0)
    override val theater = theaterRepository.findTheater(reserveOption.theaterName)
    override val seatSelectSystem: SeatSelectSystem = SeatSelectSystem(theater.seatInfo, reserveOption.peopleCount)
    override val priceSystem: PriceSystem = PriceSystem(PriceCalculator(theater.discountPolicies), reserveOption.screeningDateTime)

    override fun onSeatClick(row: Int, col: Int) {
        val result = seatSelectSystem.select(row, col)
        val index = rowColToIndex(row, col)
        when (result) {
            is SelectResult.Success.Selection -> {
                view.setSelectionSeat(index, result.isSelectAll)
            }
            is SelectResult.Success.Deselection -> {
                view.setDeselectionSeat(index)
            }
            is SelectResult.MaxSelection -> {
                view.maxSelectionToast()
            }
            is SelectResult.WrongInput -> {
                view.wrongInputToast()
            }
        }
        val newPrice = priceSystem.getCurrentPrice(price, result)
        price = newPrice
        view.setPrice(newPrice.toUiModel())
    }

    override fun onReserveClick() {
        val reservation = Reservation(reserveOption.title, reserveOption.screeningDateTime, seatSelectSystem.seats, price)
        view.showSubmitDialog(reservation.toUiModel())
    }

    override fun getSeatInfoUiModel(colorOfGrade: Map<Grade, Int>): SeatInfoUiModel {
        return theater.seatInfo.toUiModel(colorOfGrade)
    }

    private fun rowColToIndex(row: Int, col: Int): Int = row * theater.seatInfo.size.col + col
}
