package woowacourse.movie.presentation.binding.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.presentation.model.movieitem.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("android:startDate", "android:endDate")
fun TextView.setMovieDate(startDate: LocalDate, endDate: LocalDate) {
    text = context.getString(
        R.string.movie_release_date,
        startDate.format(DateTimeFormatter.ofPattern(Movie.MOVIE_DATE_PATTERN)),
        endDate.format(DateTimeFormatter.ofPattern(Movie.MOVIE_DATE_PATTERN))
    )
}

@BindingAdapter("android:runningTime")
fun TextView.setRunningTime(minute: Int) {
    text = context.getString(R.string.movie_running_time, minute)
}

@BindingAdapter("android:drawableRes")
fun ImageView.setDrawableRes(@DrawableRes resId: Int) {
    setBackgroundResource(resId)
}
