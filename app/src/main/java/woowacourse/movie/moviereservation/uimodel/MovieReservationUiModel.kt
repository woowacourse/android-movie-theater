package woowacourse.movie.moviereservation.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.R

data class MovieReservationUiModel(
    val id: Long,
    val title: String,
    @DrawableRes val imageRes: Int = R.drawable.img_movie_poster,
    val startDate: String,
    val endDate: String,
    val description: String,
    val runningTime: Int,
)
