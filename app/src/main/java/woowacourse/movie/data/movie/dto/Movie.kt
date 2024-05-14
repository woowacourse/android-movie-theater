package woowacourse.movie.data.movie.dto

import androidx.annotation.DrawableRes
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater

data class Movie(
    val id: Long,
    @DrawableRes
    val thumbnail: Int,
    val title: String,
    val description: String,
    val date: MovieDate,
    val runningTime: Int,
    val theaters: List<Theater>,
)
