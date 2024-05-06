package woowacourse.movie.presentation.ui.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("startDate", "endDate")
fun TextView.formatScreeningDate(
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedStartDate = startDate.format(formatter)
    val formattedEndDate = endDate.format(formatter)
    val resultText =
        (this.context).getString(R.string.screening_period, formattedStartDate, formattedEndDate)
    this.text = resultText
}

@BindingAdapter("bindFormatRunningTime")
fun TextView.formatRunningTime(runningTime: Int) {
    this.text = (this.context).getString(R.string.running_time, runningTime)
}
