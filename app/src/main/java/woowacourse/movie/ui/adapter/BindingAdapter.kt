package woowacourse.movie.ui.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.ui.util.PosterMapper
import woowacourse.movie.ui.util.TicketUiFormatter.formatAmount
import woowacourse.movie.ui.util.TicketUiFormatter.formatDateTime
import woowacourse.movie.ui.util.TicketUiFormatter.formatHeadCount
import woowacourse.movie.ui.util.toUi
import java.time.LocalDateTime

@BindingAdapter("posterImage")
fun ImageView.setPosterImage(title: String?) {
    if (title == null) return
    setImageResource(PosterMapper.convertTitleToResId(title))
}

@BindingAdapter("imgRes")
fun ImageView.setImageViewResource(resId: Bitmap) {
    setImageBitmap(resId)
}

@BindingAdapter("formattedDateTime")
fun TextView.setFormattedDateTime(dateTime: LocalDateTime) {
    text = formatDateTime(dateTime)
}

@BindingAdapter("formattedHeadCount")
fun TextView.setFormattedDateTime(headCount: Int) {
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
