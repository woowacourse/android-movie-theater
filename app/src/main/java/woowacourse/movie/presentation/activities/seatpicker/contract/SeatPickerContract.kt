package woowacourse.movie.presentation.activities.seatpicker.contract

import android.os.Bundle
import woowacourse.movie.presentation.model.PickedSeats

interface SeatPickerContract {
    interface View {
        val presenter: Presenter

        fun saveViewState(bundle: Bundle, pickedSeats: PickedSeats)
        fun initView(rowSize: Int, colSize: Int)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun onCreate()
        abstract fun onSaveState(bundle: Bundle)
    }
}
