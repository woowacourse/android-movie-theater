package woowacourse.movie.moviedetail.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

data class MovieDetailUiModel(
    val title: String,
    @DrawableRes val imageRes: Int = R.drawable.img_movie_poster,
    val screeningDate: String,
    val description: String,
    val runningTime: String,
) {
    constructor(
        title: String,
        startDate: LocalDate,
        endDate: LocalDate,
        description: String,
        runningTime: Duration,
    ) : this(
        title = title,
        screeningDate = "상영일: ${startDate.format(dateFormatter)} ~ ${endDate.format(dateFormatter)}",
        description = description,
        runningTime = "러닝타임: ${runningTime.inWholeMinutes}",
    )

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}
