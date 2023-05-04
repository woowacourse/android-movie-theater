package woowacourse.movie.feature.detail.dateTime

import com.example.domain.usecase.GetMovieRunningTimeUseCase
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimePresenter(
    val view: DateTimeContract.View,
    movieState: MovieState
) : DateTimeContract.Presenter {
    private val getMovieRunningTimeUseCase = GetMovieRunningTimeUseCase()

    private var runningDates: List<LocalDate> = movieState.asDomain().runningDates.toList()

    private var _selectDate: LocalDate = runningDates.first()
    override val selectDate: LocalDate
        get() = _selectDate

    private var runningTimes: List<LocalTime> = getMovieRunningTimeUseCase(selectDate).toList()

    private var _selectTime: LocalTime = runningTimes.first()
    override val selectTime: LocalTime
        get() = _selectTime

    init {
        view.setDateSpinnerAdapter(runningDates.toList())
        view.setTimeSpinnerAdapter(runningTimes.toList())
    }

    override fun setDateTime(dateTime: LocalDateTime) {
        _selectDate = dateTime.toLocalDate()
        updateRunningTimes()
        _selectTime = dateTime.toLocalTime()
        view.setSelectDate(runningDates.indexOf(selectDate))
        view.setSelectTime(runningTimes.indexOf(selectTime))
    }

    override fun clickDate(position: Int) {
        _selectDate = runningDates[position]
        updateRunningTimes()
        _selectTime = runningTimes.first()
    }

    override fun clickTime(position: Int) {
        _selectTime = runningTimes[position]
    }

    private fun updateRunningTimes() {
        runningTimes = getMovieRunningTimeUseCase(selectDate)
        view.setTimeSpinnerAdapter(runningTimes.toList())
    }
}
