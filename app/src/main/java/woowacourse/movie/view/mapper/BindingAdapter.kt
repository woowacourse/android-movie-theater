package woowacourse.movie.view.mapper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.view.mapper.Formatter.localDateToUI
import java.time.LocalDate

@BindingAdapter("android:startDate", "android:endDate", requireAll = true)
fun formatLocalDate(
    textView: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val startDate: String = localDateToUI(startDate)
    val endDate: String = localDateToUI(endDate)

    textView.text = "상영일: %s ~ %s".format(startDate, endDate)
}

@BindingAdapter("android:imageRes")
fun setImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}
