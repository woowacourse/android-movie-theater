package woowacourse.movie.feature.reservation.reserve.selection

import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.mapper.asDomain
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeSpinnerPresenter(
    val view: DateTimeSpinnerContract.View,
    theaterMovieState: SelectTheaterAndMovieState
) : DateTimeSpinnerContract.Presenter {

    private var runningDates: List<LocalDate> =
        theaterMovieState.movie.asDomain().runningDates.toList()
    override var selectedDate: LocalDate = runningDates.first()
        private set

    private var runningTimes: List<LocalTime> =
        theaterMovieState.allowTimes.toList()
    override var selectedTime: LocalTime = runningTimes.first()
        private set

    init {
        view.setDateSpinnerAdapter(runningDates.toList())
        view.setTimeSpinnerAdapter(runningTimes.toList())
    }

    override fun setDateTime(dateTime: LocalDateTime) {
        selectedDate = dateTime.toLocalDate()
        selectedTime = dateTime.toLocalTime()
        view.setSelectDate(runningDates.indexOf(selectedDate))
        view.setSelectTime(runningTimes.indexOf(selectedTime))
    }

    override fun clickDate(position: Int) {
        selectedDate = runningDates[position]
    }

    override fun clickTime(position: Int) {
        selectedTime = runningTimes[position]
    }
}
