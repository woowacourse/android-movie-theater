package woowacourse.movie.model.theater

import java.io.Serializable
import java.time.LocalTime

data class ScreeningInfo(
    val theater: Theater,
    val screeningTimes: List<LocalTime>,
) : Serializable
