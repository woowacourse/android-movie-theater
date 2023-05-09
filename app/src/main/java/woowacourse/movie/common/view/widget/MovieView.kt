package woowacourse.movie.common.view.widget

import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.common.model.MovieViewData
import java.time.format.DateTimeFormatter

data class MovieView(
    val poster: ImageView? = null,
    val title: TextView? = null,
    val date: TextView? = null,
    val runningTime: TextView? = null,
    val description: TextView? = null
) {

    fun bind(movie: MovieViewData) {
        poster?.setImageResource(movie.poster.resource)
        title?.text = movie.title

        if (date != null) {
            val dateFormat =
                DateTimeFormatter.ofPattern(date.context.getString(R.string.movie_date_format))
            date.text = date.context.getString(R.string.movie_date).format(
                dateFormat.format(movie.date.startDate), dateFormat.format(movie.date.endDate)
            )
        }

        if (runningTime != null) {
            runningTime.text =
                runningTime.context.getString(R.string.movie_running_time).format(movie.runningTime)
        }
        description?.text = movie.description
    }
}
