package woowacourse.movie.model.movie

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class ScreeningDateTime(
    val date: LocalDate,
    val time: LocalTime,
) : Serializable
