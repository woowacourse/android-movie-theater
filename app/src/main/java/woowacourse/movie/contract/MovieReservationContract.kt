package woowacourse.movie.contract

import woowacourse.movie.model.TheaterUiModel
import java.time.LocalDate
import java.time.LocalTime

interface MovieReservationContract {
    interface Presenter {
        fun updateDateSpinner(theaterUiModel: TheaterUiModel)
        fun onSelectDate(theaterUiModel: TheaterUiModel, date: LocalDate)
        fun onPlusTicketCount()
        fun onMinusTicketCount()
        fun onReservationButtonClick()
    }

    interface View {
        val presenter: Presenter
        fun setDateSpinner(date: List<LocalDate>)
        fun setTimeSpinner(times: List<LocalTime>)
        fun setCounterText(count: Int)
        fun startSeatSelectActivity(peopleCount: Int)
    }
}
