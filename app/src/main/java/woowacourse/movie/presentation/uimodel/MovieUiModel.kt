package woowacourse.movie.presentation.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.model.Movie
import java.time.format.DateTimeFormatter

data class MovieUiModel(
    val movieId: Int,
    @DrawableRes
    val posterImageId: Int,
    val title: String,
    val screeningStartDate: String,
    val screeningEndDate: String,
    val runningTime: Int,
    val summary: String,
) {
    constructor(movie: Movie) : this(
        movie.movieId,
        movie.posterImageId,
        movie.title,
        movie.screeningInfo.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
        movie.screeningInfo.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
        movie.screeningInfo.runningTime,
        movie.summary,
    )

    fun joinScreeningDates(): String {
        return "상영일 : $screeningStartDate ~ $screeningEndDate"
    }

    fun runningTimeString(): String {
        return "러닝타임 : ${runningTime}분"
    }
}
