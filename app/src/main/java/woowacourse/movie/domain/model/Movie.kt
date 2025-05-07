package woowacourse.movie.domain.model

import java.io.Serializable

data class Movie(
    val id: Long,
    val title: String,
    val posterId: Int,
    val releaseDate: ScreeningPeriod,
    val runningTime: Int,
) : Serializable
