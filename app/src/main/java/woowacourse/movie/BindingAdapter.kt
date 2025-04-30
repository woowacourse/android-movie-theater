package woowacourse.movie

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.util.Formatter.formatDateDotSeparated
import java.time.LocalDate

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("startDate", "endDate")
    fun setScreeningPeriod(
        textView: TextView,
        screeningStartDate: LocalDate,
        screeningEndDate: LocalDate,
    ) {
        val formattedStartDate = formatDateDotSeparated(screeningStartDate)
        val formattedEndDate = formatDateDotSeparated(screeningEndDate)
        val formattedPeriod = textView.context.getString(R.string.screening_date_period, formattedStartDate, formattedEndDate)

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
