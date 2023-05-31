package woowacourse.movie.feature.reservation.reserve.selection

import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.util.setClickListener
import woowacourse.movie.util.setDefaultAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeSpinner(
    val binding: ActivityMovieDetailBinding,
    theaterMovieState: SelectTheaterAndMovieState,
    initLocalDateTime: LocalDateTime? = null
) : DateTimeSpinnerContract.View {

    private val presenter: DateTimeSpinnerContract.Presenter = DateTimeSpinnerPresenter(this, theaterMovieState)

    val selectedDateTime: LocalDateTime
        get() = LocalDateTime.of(presenter.selectedDate, presenter.selectedTime)

    init {
        initLocalDateTime?.let { presenter.setDateTime(it) }
        binding.dateSpinner.setClickListener({ position -> presenter.clickDate(position) })
        binding.timeSpinner.setClickListener({ position -> presenter.clickTime(position) })
    }

    override fun setDateSpinnerAdapter(dates: List<LocalDate>) {
        binding.dateSpinner.setDefaultAdapter(dates)
    }

    override fun setTimeSpinnerAdapter(times: List<LocalTime>) {
        binding.timeSpinner.setDefaultAdapter(times)
    }

    override fun setSelectDate(position: Int) {
        binding.dateSpinner.setSelection(position, false)
    }

    override fun setSelectTime(position: Int) {
        binding.timeSpinner.setSelection(position, false)
    }
}
