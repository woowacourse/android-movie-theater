package woowacourse.movie.view.movies.model

import java.io.Serializable
import java.time.LocalDateTime

data class ScreeningInfo(
    val movieId: Int,
    val theaterName: String,
    val screening: List<LocalDateTime>,
) : Serializable
