package woowacourse.movie.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.presentation.seats.mode.toUiModel

@BindingAdapter("posterImage")
fun ImageView.setPosterImage(movie: Movie?) {
    if (movie == null) return
    setImageResource(MovieData.getDrawableResId(movie))
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
