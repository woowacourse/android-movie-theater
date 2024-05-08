package woowacourse.movie.data.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.data.ReservationHistoryEntity

object MovieDataBindingAdapter {
    @BindingAdapter("imgRes")
    @JvmStatic
    fun setImageViewResource(
        imageView: ImageView,
        @DrawableRes
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }

    @BindingAdapter("information")
    @JvmStatic
    fun setReservationHistoryInformationText(
        textView: TextView,
        reservationHistory: ReservationHistoryEntity,
    ) {
        textView.text =
            textView.context.getString(
                R.string.history_information,
                reservationHistory.date,
                reservationHistory.time,
                reservationHistory.theaterName,
            )
    }
}
