package woowacourse.movie.view.reservation

import woowacourse.movie.domain.movie.ScreeningDateTimes
import woowacourse.movie.util.getOrEmptyList
import woowacourse.movie.view.model.MovieUiModel
import java.time.LocalDate

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val movie: MovieUiModel,
    private val theater: String,
) :
    ReservationContract.Presenter {
    private var peopleCountSaved = 1
    private val screeningDateTimes: ScreeningDateTimes

    init {
        val startDate = movie.startDate
        val endDate = movie.endDate
        val times = movie.schedule.getOrEmptyList(theater)
        screeningDateTimes = ScreeningDateTimes.of(startDate, endDate, times)
    }

    override fun fetchViewData() {
        view.setViewData(movie, theater)
    }

    override fun fetchScreeningDates() {
        view.showScreeningDate(screeningDateTimes.dateTimes.keys.toList())
    }

    override fun fetchScreeningTimes(date: LocalDate) {
        val times = screeningDateTimes.dateTimes[date]
        times?.let { view.showScreeningTimes(it) }
    }

    override fun minusCount() {
        if (peopleCountSaved > 1) {
            peopleCountSaved--
            view.setCount(peopleCountSaved)
        }
    }

    override fun plusCount() {
        if (peopleCountSaved < 20) {
            peopleCountSaved++
            view.setCount(peopleCountSaved)
        }
    }
}
