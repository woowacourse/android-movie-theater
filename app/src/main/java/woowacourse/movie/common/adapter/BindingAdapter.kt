package woowacourse.movie.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.common.util.PosterMapper
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.presentation.seats.mode.toUiModel

@BindingAdapter("posterImage")
fun ImageView.setPosterImage(title: String?) {
    if (title == null) return
    setImageResource(PosterMapper.convertTitleToResId(title))
}

@BindingAdapter("imgRes")
fun ImageView.setImageViewResource(
    @DrawableRes resId: Int,
) {
    setImageResource(resId)
}

@BindingAdapter("seats")
fun TextView.setSeats(seats: Seats) {
    text = seats.seats.map { it.toUiModel() }.joinToString()
}
