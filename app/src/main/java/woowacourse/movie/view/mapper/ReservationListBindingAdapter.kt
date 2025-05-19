package woowacourse.movie.view.mapper

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("android:reservationListDate")
fun setReservationListDate(
    view: TextView,
    date: LocalDateTime,
) {
    view.text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
}

@BindingAdapter("android:reservationListTime")
fun setReservationListTime(
    view: TextView,
    date: LocalDateTime,
) {
    view.text = date.format(DateTimeFormatter.ofPattern("HH:mm"))
}
