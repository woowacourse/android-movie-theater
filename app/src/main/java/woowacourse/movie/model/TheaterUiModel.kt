package woowacourse.movie.model

import java.io.Serializable

data class TheaterUiModel(
    val id: Long,
    val name: String,
    val numberOfScreeningTimes: Int
) : Serializable
