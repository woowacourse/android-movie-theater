package woowacourse.movie.feature.detail.dateTime

import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.TheaterMovieState
import woowacourse.movie.util.setClickListener
import woowacourse.movie.util.setDefaultAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeSpinner(
    val binding: ActivityMovieDetailBinding,
    theaterMovieState: TheaterMovieState,
    initLocalDateTime: LocalDateTime? = null
) : DateTimeContract.View {
    val presenter: DateTimeContract.Presenter = DateTimePresenter(this, theaterMovieState)

    val selectDateTime: LocalDateTime
        get() = LocalDateTime.of(presenter.selectDate, presenter.selectTime)

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
