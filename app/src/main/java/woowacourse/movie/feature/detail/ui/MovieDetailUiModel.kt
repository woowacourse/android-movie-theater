package woowacourse.movie.feature.detail.ui

import androidx.annotation.DrawableRes
import woowacourse.movie.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MovieDetailUiModel(
    @DrawableRes
    val thumbnail: Int,
    val title: String,
    val description: String,
    val screeningStartDate: String,
    val screeningEndDate: String,
    val runningTime: Int,
    val screeningDates: List<String>,
    val screeningTimes: List<String>,
) {
    companion object {
        fun of(
            movie: Movie,
            theaterPosition: Int,
        ): MovieDetailUiModel {
            return MovieDetailUiModel(
                movie.thumbnail,
                movie.title,
                movie.description,
                movie.date.startLocalDate.screeningRangeMessage(),
                movie.date.endLocalDate.screeningRangeMessage(),
                movie.runningTime,
                movie.date.generateDates().map { it.formatSpinnerMessage() },
                movie.theaters[theaterPosition].screeningTimes.map { it.formatSpinnerMessage() },
            )
        }

        private fun LocalDate.screeningRangeMessage(): String {
            return format(DateTimeFormatter.ofPattern("yyyy.M.d"))
        }
    }
}
