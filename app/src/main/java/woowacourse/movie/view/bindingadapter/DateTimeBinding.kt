package woowacourse.movie.view.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("dateTime")
fun setDateTime(
    view: TextView,
    date: LocalDateTime,
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy.M.d. HH:mm")
    val dateTimeFormat = date.format(formatter)
    view.text = dateTimeFormat.toString()
}
