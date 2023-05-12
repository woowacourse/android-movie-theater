package woowacourse.movie.view.activities.screeningdetail

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import java.time.format.DateTimeFormatter

class ScreeningDetailViewHolder(private val activity: Activity) {
    private val posterView = activity.findViewById<ImageView>(R.id.movie_poster_iv)
    private val movieTitleView = activity.findViewById<TextView>(R.id.movie_title_tv)
    private val screeningRangeView = activity.findViewById<TextView>(R.id.screening_range_tv)
    private val runningTimeView = activity.findViewById<TextView>(R.id.running_time_tv)
    private val summaryView = activity.findViewById<TextView>(R.id.movie_summary_tv)

    fun bind(screeningDetailUIState: ScreeningDetailUIState) {
        posterView.setImageResource(screeningDetailUIState.poster)
        movieTitleView.text = screeningDetailUIState.title
        screeningRangeView.text = activity.getString(R.string.screening_range_format)
            .format(
                DATE_FORMATTER.format(screeningDetailUIState.screeningStartDate),
                DATE_FORMATTER.format(screeningDetailUIState.screeningEndDate)
            )
        runningTimeView.text = activity.getString(R.string.running_time_format)
            .format(screeningDetailUIState.runningTime)
        summaryView.text = screeningDetailUIState.summary
    }

    companion object {
        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}