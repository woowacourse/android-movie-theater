package woowacourse.movie.view.mapper

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.ReservationCount
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("android:price")
fun setPrice(
    view: TextView,
    price: Int,
) {
    val text = view.context.getString(R.string.reservation_total_money, price)
    view.text = text
}

@BindingAdapter("android:reservationDateTime")
fun setReservationDateTime(
    view: TextView,
    reservationDateTime: LocalDateTime,
) {
    val text =
        reservationDateTime.format(
            DateTimeFormatter.ofPattern(
                view.context.getString(R.string.reservation_datetime_format),
            ),
        )
    view.text = text
}

@BindingAdapter("android:reservationCount")
fun setReservationCount(
    view: TextView,
    reservationCount: ReservationCount,
) {
    view.text =
        view.context.getString(
            R.string.reservation_count_info,
        ).format(reservationCount.value)
}
