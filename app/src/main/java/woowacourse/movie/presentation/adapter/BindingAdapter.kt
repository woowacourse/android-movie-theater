package woowacourse.movie.presentation.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.presentation.util.PosterMapper
import woowacourse.movie.presentation.util.TicketUiFormatter.formatAmount
import woowacourse.movie.presentation.util.TicketUiFormatter.formatDateTime
import woowacourse.movie.presentation.util.TicketUiFormatter.formatHeadCount
import woowacourse.movie.presentation.util.toUi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("posterImage")
fun ImageView.setPosterImage(title: String?) {
    if (title == null) return
    setImageResource(PosterMapper.convertTitleToResId(title))
}

@BindingAdapter("imgRes")
fun ImageView.setImageViewResource(resId: Drawable) {
    setImageDrawable(resId)
}

@BindingAdapter("formattedDateTime")
fun TextView.setFormattedDateTime(dateTime: LocalDateTime) {
    text = formatDateTime(dateTime)
}

@BindingAdapter("formattedHeadCount")
fun TextView.setFormattedHeadCount(headCount: Int) {
    text = formatHeadCount(context.getString(R.string.headCount_message), headCount)
}

@BindingAdapter("formattedAmount")
fun TextView.setFormattedAmount(amount: Int) {
    text = formatAmount(context.getString(R.string.summary_amount_message), amount)
}

@BindingAdapter("seats")
fun TextView.setSeats(seats: List<Seat>) {
    text = seats.toUi()
}

@BindingAdapter("formattedDate")
fun TextView.setFormattedDate(dateTime: LocalDateTime?) {
    dateTime?.let {
        text = it.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy.M.dd"))
    }
}

@BindingAdapter("formattedTime")
fun TextView.setFormattedTime(dateTime: LocalDateTime?) {
    dateTime?.let {
        text = it.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}
