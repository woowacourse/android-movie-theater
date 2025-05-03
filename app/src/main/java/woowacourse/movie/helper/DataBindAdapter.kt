package woowacourse.movie.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.helper.LocalDateHelper.toDotFormat
import java.time.LocalDate
import java.time.LocalDateTime

@BindingAdapter("startDate", "endDate", requireAll = true)
fun setFormattedLocalDate(
    view: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    view.text = view.context.getString(R.string.movie_screening_date, startDate.toDotFormat(), endDate.toDotFormat())
}

@BindingAdapter("formattedLocalDateTime")
fun setFormattedLocalDateTime(
    view: TextView,
    localDateTime: LocalDateTime,
) {
    view.text = localDateTime.toDotFormat()
}

@BindingAdapter("drawableResource")
fun setDrawableResource(
    view: ImageView,
    drawableRes: Int,
) {
    view.setImageResource(drawableRes)
}

@BindingAdapter("seat")
fun setFormattedSeat(
    view: TextView,
    seats: List<Seat>,
) {
    view.text =
        seats.joinToString(", ") { seat ->
            val rowChar = 'A' + seat.row.value
            val colNumber = seat.column.value + 1
            "$rowChar$colNumber"
        }
}
