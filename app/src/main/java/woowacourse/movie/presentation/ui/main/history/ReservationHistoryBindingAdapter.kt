package woowacourse.movie.presentation.ui.main.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("bindFormatScreeningDate")
fun TextView.formatScreeningDate(date: LocalDateTime) {
    val formatter = DateTimeFormatter.ofPattern("yyyy.M.d")
    val formattedDate = date.format(formatter)
    text = formattedDate
}

@BindingAdapter("bindFormatScreeningTime")
fun TextView.formatScreeningTime(date: LocalDateTime) {
    val formatter = DateTimeFormatter.ofPattern("HH.mm")
    val formattedTime = date.format(formatter)
    text = formattedTime
}
