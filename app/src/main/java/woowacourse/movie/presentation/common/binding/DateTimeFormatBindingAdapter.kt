package woowacourse.movie.presentation.common.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("dateTime", "formatPattern")
fun TextView.setFormattedDateTime(
    dateTime: LocalDateTime,
    formatPattern: String?,
) {
    runCatching {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        dateTime.format(formatter)
    }.onFailure {
        this.text = dateTime.toString()
    }.onSuccess {
        this.text = it
    }
}
