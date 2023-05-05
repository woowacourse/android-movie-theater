package woowacourse.movie.contract

import java.time.LocalDate
import java.time.LocalTime

interface MovieReservationContract {
    interface Presenter {
        fun onSelectDate(date: LocalDate)
        fun onPlusTicketCount()
        fun onMinusTicketCount()
        fun onReservationButtonClick()
    }

    interface View {
        val presenter: Presenter
        fun setTimeSpinner(times: List<LocalTime>)
        fun setCounterText(count: Int)
        fun startSeatSelectActivity(peopleCount: Int)
    }
}
