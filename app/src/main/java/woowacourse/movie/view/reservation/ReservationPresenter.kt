package woowacourse.movie.view.reservation

import woowacourse.movie.domain.movie.ScreeningTime
import java.time.LocalDate

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

    override fun onDateSpinnerChanged(date: LocalDate) {
        view.setTimeSpinner(ScreeningTime(date).getAllScreeningTimes())
    }
}
