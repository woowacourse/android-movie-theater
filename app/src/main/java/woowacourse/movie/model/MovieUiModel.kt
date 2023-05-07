package woowacourse.movie.model

import androidx.annotation.DrawableRes
import java.io.Serializable
import java.time.LocalDate
import java.time.Period

data class MovieUiModel(
    @DrawableRes val picture: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val theaters: TheatersUiModel,
    val runningTime: Int,
    val description: String,
) : UiModel, Serializable
