package woowacourse.movie.ui.view.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter(value = ["formatDate", "formatPattern"])
fun setDateFormat(
    view: TextView,
    localDateTime: LocalDateTime?,
    format: String?,
) {
    val formatter = DateTimeFormatter.ofPattern(format)
    view.text = formatter.format(localDateTime)
}

@BindingAdapter(value = ["formatTime", "formatPattern"])
fun setTimeFormat(
    view: TextView,
    localDateTime: LocalDateTime?,
    format: String?,
) {
    val formatter = DateTimeFormatter.ofPattern(format)
    view.text = formatter.format(localDateTime)
}
