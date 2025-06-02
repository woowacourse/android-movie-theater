package woowacourse.movie.view

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TIME_FORMAT = "yyyy.M.d | HH:mm"

@BindingAdapter("formattedDateTime")
fun TextView.setFormattedDateTime(dateTime: LocalDateTime?) {
    dateTime?.let {
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
        text = it.format(formatter)
    }
}
