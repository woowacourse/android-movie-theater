package woowacourse.movie.ui.reservation

import android.view.View
import android.widget.Spinner
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import woowacourse.movie.R
import woowacourse.movie.model.MovieState
import woowacourse.movie.util.setClickListener
import woowacourse.movie.util.setDefaultAdapter

class DateTimeSpinner(
    view: View,
    movieState: MovieState,
    getDates: (MovieState) -> List<LocalDate>,
    getTimes: (MovieState) -> List<LocalTime>,
    initLocalDateTime: LocalDateTime? = null
) {
    private val dateSpinner: Spinner = view.findViewById(R.id.date_spinner)
    private val timeSpinner: Spinner = view.findViewById(R.id.time_spinner)

    private val runningDates: List<LocalDate>
    private var selectDate: LocalDate = LocalDate.now()
        set(value) {
            field = value
            setTimeSpinnerAdapter()
        }

    private var runningTimes: List<LocalTime> = listOf()
        set(value) {
            field = value
            selectTime = field.first()
        }
    private var selectTime: LocalTime

    init {
        runningDates = getDates(movieState)
        setDateSpinnerAdapter()
        selectDate = runningDates.first()
        runningTimes = getTimes(movieState)
        setTimeSpinnerAdapter()
        selectTime = runningTimes.first()

        initLocalDateTime?.let { updateSelectDateTime(it.toLocalDate(), it.toLocalTime()) }

        dateSpinner.setClickListener({ position -> selectDate = runningDates[position] })
        timeSpinner.setClickListener({ position -> selectTime = runningTimes[position] })
    }

    fun getSelectDateTime(): LocalDateTime = LocalDateTime.of(selectDate, selectTime)

    private fun updateSelectDateTime(selectLocalDate: LocalDate, selectLocalTime: LocalTime) {
        selectDate = selectLocalDate
        selectTime = selectLocalTime
        dateSpinner.setSelection(runningDates.indexOf(selectDate), false)
        timeSpinner.setSelection(runningTimes.indexOf(selectTime), false)
    }

    private fun setDateSpinnerAdapter() {
        dateSpinner.setDefaultAdapter(runningDates.map { it.toString() })
    }

    private fun setTimeSpinnerAdapter() {
        timeSpinner.setDefaultAdapter(runningTimes.map { it.toString() })
    }
}
