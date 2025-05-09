package woowacourse.movie.domain.model.movie

import java.io.Serializable

data class Movie(
    val title: String,
    val posterId: Int,
    val releaseDate: ScreeningPeriod,
    val runningTime: Int,
) : Serializable
