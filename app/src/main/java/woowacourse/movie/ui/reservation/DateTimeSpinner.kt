package woowacourse.movie.ui.reservation

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.util.setDefaultAdapter

class DateTimeSpinner(
    private val binding: ActivityMovieDetailBinding,
    private var runningDates: List<LocalDate>,
    private var runningTimes: List<LocalTime>
) {

    init {
        binding.dateSpinner.setDefaultAdapter(runningDates.map { it.toString() })
        binding.timeSpinner.setDefaultAdapter(runningTimes.map { it.toString() })
    }

    fun getSelectDateTime(): LocalDateTime = LocalDateTime.of(
        runningDates[binding.dateSpinner.selectedItemPosition],
        runningTimes[binding.timeSpinner.selectedItemPosition]
    )

    fun updateSelectDateTime(selectLocalDate: LocalDate, selectLocalTime: LocalTime) {
        binding.dateSpinner.setSelection(runningDates.indexOf(selectLocalDate), false)
        binding.timeSpinner.setSelection(runningTimes.indexOf(selectLocalTime), false)
    }
}
