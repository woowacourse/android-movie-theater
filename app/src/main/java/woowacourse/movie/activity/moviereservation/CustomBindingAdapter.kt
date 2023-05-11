package woowacourse.movie.activity.moviereservation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.view.data.DateRangeViewData
import java.time.format.DateTimeFormatter

object CustomBindingAdapter {
    @BindingAdapter("movieDateFormat", "movieScreeningFormat", "movieDate")
    @JvmStatic
    fun formatScreeningDate(
        view: TextView,
        movieDateFormat: String,
        movieScreeningFormat: String,
        movieDate: DateRangeViewData
    ) {
        val dateFormat = DateTimeFormatter.ofPattern(movieDateFormat)
        val formattedText = movieScreeningFormat.format(
            dateFormat.format(movieDate.startDate), dateFormat.format(movieDate.endDate)
        )
        view.text = formattedText
    }

    @BindingAdapter("movieRunningTimeFormat", "movieRunningTime")
    @JvmStatic
    fun formatRunningTime(
        view: TextView,
        movieRunningTimeFormat: String,
        movieRunningTime: Int
    ) {
        view.text = movieRunningTimeFormat.format(movieRunningTime)
    }

    @BindingAdapter("moviePosterRes")
    @JvmStatic
    fun loadPoster(
        view: ImageView,
        moviePosterRes: Int
    ) {
        view.setImageResource(moviePosterRes)
    }
}
