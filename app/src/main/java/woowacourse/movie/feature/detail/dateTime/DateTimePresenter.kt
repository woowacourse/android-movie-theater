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
    override var selectDate: LocalDate = runningDates.first()

    private var runningTimes: List<LocalTime> = getMovieRunningTimeUseCase(selectDate).toList()
    override var selectTime: LocalTime = runningTimes.first()

    init {
        view.setDateSpinnerAdapter(runningDates.toList())
        view.setTimeSpinnerAdapter(runningTimes.toList())
    }

    override fun setDateTime(dateTime: LocalDateTime) {
        selectDate = dateTime.toLocalDate()
        updateRunningTimes()
        selectTime = dateTime.toLocalTime()
        view.setSelectDate(runningDates.indexOf(selectDate))
        view.setSelectTime(runningTimes.indexOf(selectTime))
    }

    override fun clickDate(position: Int) {
        selectDate = runningDates[position]
        updateRunningTimes()
        selectTime = runningTimes.first()
    }

    override fun clickTime(position: Int) {
        selectTime = runningTimes[position]
    }

    private fun updateRunningTimes() {
        runningTimes = getMovieRunningTimeUseCase(selectDate)
        view.setTimeSpinnerAdapter(runningTimes.toList())
    }
}
