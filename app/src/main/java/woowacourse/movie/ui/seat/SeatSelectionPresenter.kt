package woowacourse.movie.ui.seat

import woowacourse.movie.domain.seat.SelectedSeats
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toModel
import woowacourse.movie.uimodel.PeopleCountModel
import woowacourse.movie.uimodel.SeatModel
import woowacourse.movie.uimodel.SelectedSeatsModel
import java.time.LocalDateTime

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    val movieTitle: String,
    val movieTime: LocalDateTime,
    val peopleCountModel: PeopleCountModel,
) : SeatSelectionContract.Presenter {
    private var selectedSeats = SelectedSeats()
    override val selectedSeatsModel: SelectedSeatsModel
        get() = selectedSeats.toModel()

    init {
        view.initMovieTitleView(movieTitle)
        updateView(0, false)
    }

    override fun updateSelectedSeatsModel(selectedSeatsModel: SelectedSeatsModel) {
        selectedSeats = selectedSeatsModel.toDomain()
        updateView(selectedSeats.getAllPrice(movieTime), isSelectionDone())
    }

    override fun clickSeat(seat: SeatModel, isSelected: Boolean) {
        selectedSeats = when (isSelected) {
            true -> {
                selectedSeats.add(seat.toDomain())
            }
            false -> {
                selectedSeats.delete(seat.toDomain())
            }
        }

        updateView(selectedSeats.getAllPrice(movieTime), isSelectionDone())
    }

    private fun updateView(price: Int, isSelectionDone: Boolean) {
        view.updatePriceText(price)
        view.updateButtonEnablement(isSelectionDone)
    }

    override fun isSelected(seat: SeatModel): Boolean {
        return selectedSeats.contains(seat.toDomain())
    }

    override fun isSelectionDone(): Boolean {
        return selectedSeats.isSelectionDone(peopleCountModel.count)
    }
}
