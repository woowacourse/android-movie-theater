package woowacourse.movie.model.theater

import woowacourse.movie.model.movie.MovieTime
import java.io.Serializable

data class ScreeningInfo(
    val screeningTimes: List<MovieTime>,
) : Serializable
