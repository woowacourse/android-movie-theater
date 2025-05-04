package woowacourse.movie.common.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.common.StringFormatter
import woowacourse.movie.common.util.PosterMapper
import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.presentation.seats.mode.toUiModel
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
    text = StringFormatter.dateTime(dateTime)
}

@BindingAdapter("formattedHeadCount")
fun TextView.setFormattedHeadCount(headCount: HeadCount) {
    text = context.getString(R.string.headCount_message, headCount.value)
}

@BindingAdapter("formattedAmount")
fun TextView.setFormattedAmount(amount: Int) {
    text = context.getString(R.string.summary_amount_message, amount)
}

@BindingAdapter("seats")
fun TextView.setSeats(seats: Seats) {
    text = seats.seats.map { it.toUiModel() }.joinToString()
}
