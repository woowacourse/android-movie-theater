package woowacourse.movie.view

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("formattedDateTime")
fun TextView.setFormattedDateTime(dateTime: LocalDateTime?) {
    dateTime?.let {
        val formatter = DateTimeFormatter.ofPattern("yyyy.M.d | HH:mm")
        text = it.format(formatter)
    }
}
