package woowacourse.movie.view.reservation

import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        var presenter: Presenter
        fun setCount(count: Int)
        fun setTimeSpinner(times: List<LocalTime>)
    }

    interface Presenter {
        fun getSchedules(movie: MovieUiModel, theater: String): Map<LocalDate, List<LocalTime>>
        fun onMinusClick()
        fun onPlusClick()
        fun onDateSpinnerChanged(position: Int, screeningDateTimes: Map<LocalDate, List<LocalTime>>)
    }
}
