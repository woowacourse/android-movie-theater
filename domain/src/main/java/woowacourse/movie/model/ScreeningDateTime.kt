package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalTime

data class ScreeningDateTime(
    val date: LocalDate,
    val times: List<LocalTime>,
)
