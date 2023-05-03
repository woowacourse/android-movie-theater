package woowacourse.movie.view.reservation

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ScreeningTime
import java.time.LocalDate

class ReservationPresenter(private val view: ReservationContract.View) :
    ReservationContract.Presenter {
    private var peopleCountSaved = 0

    override fun onMinusClick() {
        if (peopleCountSaved > Reservation.MIN_PEOPLE_COUNT) {
            peopleCountSaved--
            view.setCount(peopleCountSaved)
        }
    }

    override fun onPlusClick() {
        if (peopleCountSaved < Reservation.MAX_PEOPLE_COUNT) {
            peopleCountSaved++
            view.setCount(peopleCountSaved)
        }
    }

    override fun onDateSpinnerChanged(date: LocalDate) {
        view.setTimeSpinner(ScreeningTime(date).getAllScreeningTimes())
    }
}
