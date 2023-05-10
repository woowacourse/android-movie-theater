package woowacourse.movie.presentation.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class MovieModel(
    val id: Long,
    val title: String,
    val screeningStartDate: LocalDate,
    val screeningEndDate: LocalDate,
    val runningTime: Int,
    val description: String,
    @DrawableRes val thumbnail: Int,
    @DrawableRes val poster: Int,
)
