package woowacourse.movie.presentation.purchaseConfirmation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("reservationTime")
fun setFormattedTime(
    view: TextView,
    localDateTime: LocalDateTime?,
) {
    view.text = localDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) ?: ""
}
