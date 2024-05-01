package woowacourse.movie.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.Formatter.formatPrice

object MovieDataBindingAdapter {
    @BindingAdapter("imgRes")
    @JvmStatic
    fun setImageViewResource(
        imageView: ImageView,
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }

    @BindingAdapter("movieTicketPrice")
    @JvmStatic
    fun setTotalPriceText(
        textView: TextView,
        movieTicket: MovieTicket,
    ) {
        textView.text = formatPrice(movieTicket.seats.totalPrice())
    }

    @BindingAdapter("movieSelectedSeatsPrice")
    @JvmStatic
    fun setTotalPriceText(
        textView: TextView,
        movieSelectedSeats: MovieSelectedSeats?,
    ) {
        textView.text = if (movieSelectedSeats != null) formatPrice(movieSelectedSeats.totalPrice()) else "0"
    }
}
