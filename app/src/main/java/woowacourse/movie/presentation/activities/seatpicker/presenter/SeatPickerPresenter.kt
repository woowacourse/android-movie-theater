package woowacourse.movie.presentation.activities.seatpicker.presenter

import android.os.Bundle
import woowacourse.movie.domain.model.seat.DomainPickedSeats
import woowacourse.movie.presentation.activities.seatpicker.contract.SeatPickerContract
import woowacourse.movie.presentation.mapper.toPresentation

class SeatPickerPresenter(
    view: SeatPickerContract.View
) : SeatPickerContract.Presenter(view) {
    private val seatRowSize: Int = 5
    private val seatColSize: Int = 4

    private var pickedSeats = DomainPickedSeats()

    override fun onCreate() {
        view.initView(seatRowSize, seatColSize)
    }

    override fun onSaveState(bundle: Bundle) {
        view.saveViewState(bundle, pickedSeats.toPresentation())
    }
}
