package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate

data class Movie(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
) : Serializable
