package woowacourse.movie.view.home.model

import java.io.Serializable
import java.time.LocalDateTime

data class ScreeningInfo(
    val movieId: Int,
    val theaterName: String,
    val screenings: List<LocalDateTime>,
) : Serializable
