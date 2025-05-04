package woowacourse.movie.view.mapper

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.RunningTime
import woowacourse.movie.domain.model.Seat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("android:startDate", "android:endDate", requireAll = true)
fun setDateFormatter(
    view: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val formatter = DateTimeFormatter.ofPattern(view.context.getString(R.string.movie_screening_period_format))
    view.text =
        view.context.getString(
            R.string.movie_date,
            startDate.format(formatter),
            endDate.format(formatter),
        )
}

@BindingAdapter("android:runningTime")
fun setRunningTime(
    view: TextView,
    runningTime: RunningTime,
) {
    view.text =
        view.context.getString(
            R.string.running_time,
            runningTime.minute.toString(),
        )
}

@BindingAdapter("android:cinemaName")
fun setCinemaName(
    view: TextView,
    cinemaName: String,
) {
    view.text =
        view.context.getString(
            R.string.cinema,
            cinemaName,
        )
}

@BindingAdapter("android:screeningTimes")
fun setScreeningItems(
    view: TextView,
    size: Int,
) {
    view.text =
        view.context.getString(
            R.string.screenig_times,
            size,
        )
}

@BindingAdapter("android:image")
fun setImage(
    view: ImageView,
    @DrawableRes image: Int,
) {
    view.setImageResource(image)
}

@BindingAdapter("android:onClick")
fun setOnClickEventListener(
    view: Button,
    listener: () -> Unit,
) {
    view.setOnClickListener {
        listener()
    }
}

@BindingAdapter("android:price")
fun setPrice(
    view: TextView,
    price: Int,
) {
    val text = view.context.getString(R.string.reservation_total_money, price)
    view.text = text
}

@BindingAdapter("android:seats")
fun setSeats(
    view: TextView,
    seats: List<Seat>,
) {
    val text =
        view.context.getString(
            R.string.seat_split_line,
            seats.joinToString(",") { it.toFormattedRow() + it.column.toString() },
        )
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

@BindingAdapter("android:totalPrice")
fun setTotalPrice(
    view: TextView,
    totalPrice: Int,
) {
    val text = view.context.getString(R.string.reservation_total_price, totalPrice)
    view.text = text
}

private fun Seat.toFormattedRow(): String {
    return (this.row + 65).toChar().toString()
}
