package woowacourse.movie.presentation.activities.seatpicker.presenter

import woowacourse.movie.domain.model.seat.DomainPickedSeats
import woowacourse.movie.presentation.activities.seatpicker.contract.SeatPickerContract
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.model.PickedSeats

class SeatPickerPresenter(view: SeatPickerContract.View) : SeatPickerContract.Presenter(view) {
    private var pickedSeats: DomainPickedSeats = DomainPickedSeats()

    override fun setPickedSeat(newPickedSeats: PickedSeats) {
        pickedSeats = newPickedSeats.toDomain()
    }
}
