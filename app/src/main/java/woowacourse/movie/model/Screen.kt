package woowacourse.movie.model

import java.time.LocalDateTime

data class Screen(
    val movieId: Long,
    val screenDateTimes: List<LocalDateTime>
)
