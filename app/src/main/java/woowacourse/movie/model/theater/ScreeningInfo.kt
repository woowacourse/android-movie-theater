package woowacourse.movie.model.theater

import java.io.Serializable
import java.time.LocalTime

data class ScreeningInfo(
    val screeningTimes: List<LocalTime>,
) : Serializable
