package woowacourse.movie.view.reservation

import java.time.LocalDate
import java.time.LocalTime

class ReservationPresenter(private val view: ReservationContract.View) :
    ReservationContract.Presenter {
    private var peopleCountSaved = 0

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
