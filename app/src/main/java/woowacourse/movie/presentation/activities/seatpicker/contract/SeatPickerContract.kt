package woowacourse.movie.presentation.activities.seatpicker.contract

import woowacourse.movie.presentation.model.PickedSeats

interface SeatPickerContract {
    interface View {
        val presenter: Presenter
    }

    abstract class Presenter(protected val view: View) {
        abstract fun setPickedSeat(newPickedSeats: PickedSeats)
    }
}
