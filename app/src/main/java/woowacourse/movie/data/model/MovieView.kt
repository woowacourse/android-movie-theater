package woowacourse.movie.data.model

import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.data.model.uimodel.MovieUIModel
import java.time.format.DateTimeFormatter

class MovieView(
    private val poster: ImageView? = null,
    private val title: TextView? = null,
    private val date: TextView? = null,
    private val runningTime: TextView? = null,
    private val description: TextView? = null
) {
    fun render(movieUiModel: MovieUIModel) {
        poster?.setImageResource(movieUiModel.picture)
        title?.text = movieUiModel.title

        val dateFormat =
            DateTimeFormatter.ofPattern(title?.context?.getString(R.string.movie_date_format))
        date?.text = date?.context?.getString(R.string.movie_date)?.format(
            dateFormat.format(movieUiModel.startDate), dateFormat.format(movieUiModel.endDate)
        )
        runningTime?.text =
            runningTime?.context?.getString(R.string.movie_running_time)
                ?.format(movieUiModel.runningTime)
        description?.text = movieUiModel.description
    }
}
