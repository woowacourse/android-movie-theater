package woowacourse.movie.domain.model.home

import java.io.Serializable
import java.time.LocalDate

data class Movie(
    val title: String,
    val posterResourceId: Int,
    val firstScreeningDate: LocalDate,
    val runningTime: Int,
    val description: String,
    val id: Long,
) : Serializable
