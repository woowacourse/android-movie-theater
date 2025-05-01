package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalTime

data class Screening(
    val theater: String,
    val movie: Movie,
    val times: List<LocalTime>,
) : Serializable
