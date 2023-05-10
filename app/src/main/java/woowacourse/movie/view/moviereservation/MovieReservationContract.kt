package woowacourse.movie.view.moviereservation

import woowacourse.movie.model.TheaterUiModel
import java.time.LocalDate
import java.time.LocalTime

interface MovieReservationContract {
    interface Presenter {
        fun updateDateSpinner(theaterUiModel: TheaterUiModel)
        fun updateTimes(theaterUiModel: TheaterUiModel, date: LocalDate)
        fun plusTicketCount()
        fun minusTicketCount()
        fun moveNextReservationStep()
    }

    interface View {
        fun setDates(date: List<LocalDate>)
        fun setTimes(times: List<LocalTime>)
        fun setCounterText(count: Int)
        fun showSelectSeatScreen(peopleCount: Int)
    }
}
