package woowacourse.movie.view.reservation

import woowacourse.movie.domain.movie.ScreeningDateTimes
import woowacourse.movie.util.getOrEmptyList
import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDate
import java.time.LocalTime

class ReservationPresenter(private val view: ReservationContract.View) :
    ReservationContract.Presenter {
    private var peopleCountSaved = 0

    override fun getSchedules(movie: MovieUiModel, theater: String): Map<LocalDate, List<LocalTime>> {
        val startDate = movie.startDate
        val endDate = movie.endDate
        val times = movie.schedule.getOrEmptyList(theater)
        return ScreeningDateTimes.of(startDate, endDate, times).dateTimes
    }

    override fun onMinusClick() {
        if (peopleCountSaved > 0) {
            peopleCountSaved--
            view.setCount(peopleCountSaved)
        }
    }

    override fun onPlusClick() {
        if (peopleCountSaved < 20) {
            peopleCountSaved++
            view.setCount(peopleCountSaved)
        }
    }

    override fun onDateSpinnerChanged(position: Int, screeningDateTimes: Map<LocalDate, List<LocalTime>>) {
        val times = screeningDateTimes[screeningDateTimes.keys.toList()[position]]
        times?.let { view.setTimeSpinner(it) }
    }
}
