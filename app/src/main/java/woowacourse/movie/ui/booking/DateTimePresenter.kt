package woowacourse.movie.ui.booking

import woowacourse.movie.movie.ScreeningDate
import woowacourse.movie.movie.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalTime

class DateTimePresenter(
    startDate: LocalDate,
    endDate: LocalDate,
    private val view: DateTimeContract.View
) : DateTimeContract.Presenter {

    override val screeningDates: List<LocalDate> =
        ScreeningPeriod(startDate, endDate).getScreeningDates().map { it.value }

    override val screeningTimes: (selectedDate: LocalDate) -> List<LocalTime> = { selectedDate ->
        ScreeningDate(selectedDate).screeningTimes
    }

    init {
        // todo 이런구조는 사용하지 말자
        view.initDateSelectedListener { screeningDate ->
            onDateSelected(screeningDate)
        }
    }

    override fun initDateTimes() {
        view.setDates(screeningDates)
        view.setTimes(screeningTimes(screeningDates.first()))
    }

    override fun onDateSelected(screeningDate: LocalDate) {
        view.setTimes(screeningTimes(screeningDate))
    }
}
