package woowacourse.movie.view.moviemain.movielist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.util.DATE_FORMATTER
import java.time.LocalDate

@BindingAdapter("startDate", "endDate", "format")
fun bindScreeningDate(
    textView: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
    format: String
) {
    textView.text = format.format(
        startDate.format(DATE_FORMATTER),
        endDate.format(DATE_FORMATTER)
    )
}
