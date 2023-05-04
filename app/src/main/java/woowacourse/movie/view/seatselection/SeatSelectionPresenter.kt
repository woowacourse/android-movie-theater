package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.system.PriceSystem
import woowacourse.movie.domain.system.SeatSelectSystem
import woowacourse.movie.domain.system.SelectResult
import woowacourse.movie.view.mapper.toUiModel
import java.time.LocalDateTime

class SeatSelectionPresenter(private val view: SeatSelectionContract.View, private val seatSystem: SeatSelectSystem, private val priceSystem: PriceSystem) : SeatSelectionContract.Presenter {
    private var price = Price(0)
    override fun onSeatClick(row: Int, col: Int) {
        val result = seatSystem.select(row, col)
        when (result) {
            is SelectResult.Success.Selection -> {
                view.setSelectionSeat(row, col, result.isSelectAll)
            }
            is SelectResult.Success.Deselection -> {
                view.setDeselectionSeat(row, col)
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

    override fun onReserveClick(title: String, screeningDateTime: LocalDateTime) {
        val reservation = Reservation(title, screeningDateTime, seatSystem.seats, price)
        view.showSubmitDialog(reservation.toUiModel())
    }
}
