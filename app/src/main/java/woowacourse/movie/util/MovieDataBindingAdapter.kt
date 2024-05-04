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
        textView.text = movieTicket.seats.totalPrice().formatPrice()
    }

    @BindingAdapter("movieSelectedSeatsPrice")
    @JvmStatic
    fun setTotalPriceText(
        textView: TextView,
        movieSelectedSeats: MovieSelectedSeats?,
    ) {
        textView.text = movieSelectedSeats?.totalPrice()?.formatPrice() ?: "0"
    }
}
