package woowacourse.movie.feature.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.model.MovieState
import woowacourse.movie.util.DateTimeFormatters

@BindingAdapter("movieDetailDates")
fun setMovieDetailDates(view: TextView, movie: MovieState) {
    view.text =
        DateTimeFormatters.convertToDateTildeDate(
            view.context,
            movie.startDate,
            movie.endDate
        )
}

@BindingAdapter("movieDetailRunningMinutes")
fun setMovieDetailRunningMinutes(view: TextView, movie: MovieState) {
    view.text = view.context.getString(R.string.running_time, movie.runningTime)
}
