package woowacourse.movie.feature.reservation.reserve.selection

import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.MovieState
import woowacourse.movie.util.setClickListener
import woowacourse.movie.util.setDefaultAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeSpinner(
    private val binding: ActivityMovieDetailBinding,
    movieState: MovieState,
    private val getDates: (MovieState) -> List<LocalDate>,
    private val getTimes: (LocalDate) -> List<LocalTime>,
    savedLocalDateTime: LocalDateTime? = null
) {

    val selectedDateTime: LocalDateTime
        get() = LocalDateTime.of(selectDate, selectTime)

    private var selectDate: LocalDate = LocalDate.now()
        set(value) {
            field = value
            runningTimes = getTimes(field)
            setTimeSpinnerAdapter()
        }
    private var selectTime: LocalTime

    private val runningDates: List<LocalDate>
    private var runningTimes: List<LocalTime> = listOf()
        set(value) {
            field = value
            selectTime = field.first()
        }

    init {
        runningDates = getDates(movieState)
        setDateSpinnerAdapter()
        selectDate = runningDates.first()
        setTimeSpinnerAdapter()
        runningTimes = getTimes(selectDate)
        selectTime = runningTimes.first()

        savedLocalDateTime?.let { updateSelectDateTime(it.toLocalDate(), it.toLocalTime()) }

        binding.dateSpinner.setClickListener({ position -> selectDate = runningDates[position] })
        binding.timeSpinner.setClickListener({ position -> selectTime = runningTimes[position] })
    }

    private fun updateSelectDateTime(selectLocalDate: LocalDate, selectLocalTime: LocalTime) {
        selectDate = selectLocalDate
        selectTime = selectLocalTime
        binding.dateSpinner.setSelection(runningDates.indexOf(selectDate), false)
        binding.timeSpinner.setSelection(runningTimes.indexOf(selectTime), false)
    }

    private fun setDateSpinnerAdapter() {
        binding.dateSpinner.setDefaultAdapter(runningDates.map { it.toString() })
    }

    private fun setTimeSpinnerAdapter() {
        binding.timeSpinner.setDefaultAdapter(runningTimes.map { it.toString() })
    }
}
