package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalTime

data class Screening(
    val theater: Theater,
    val movie: Movie,
    val times: List<LocalTime>,
) : Serializable
