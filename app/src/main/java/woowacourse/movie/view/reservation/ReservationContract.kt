package woowacourse.movie.view.reservation

import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        var presenter: Presenter
        fun setCount(count: Int)
        fun setTimeSpinner(times: List<LocalTime>)
    }

    interface Presenter {
        fun onMinusClick()
        fun onPlusClick()
        fun onDateSpinnerChanged(date: LocalDate)
    }
}
