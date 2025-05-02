package woowacourse.movie.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import java.time.LocalDate

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("startDate", "endDate")
    fun setScreeningPeriod(
        textView: TextView,
        screeningStartDate: LocalDate,
        screeningEndDate: LocalDate,
    ) {
        val formattedStartDate = Formatter.formatDateDotSeparated(screeningStartDate)
        val formattedEndDate = Formatter.formatDateDotSeparated(screeningEndDate)
        val formattedPeriod = textView.context.getString(R.string.formatted_screening_date_period, formattedStartDate, formattedEndDate)

        textView.text = formattedPeriod
    }

    @JvmStatic
    @BindingAdapter("imageId")
    fun setMoviePoster(
        imageView: ImageView,
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }
}
