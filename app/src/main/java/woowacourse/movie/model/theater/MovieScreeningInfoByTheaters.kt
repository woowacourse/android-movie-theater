package woowacourse.movie.model.theater

import java.io.Serializable

data class MovieScreeningInfoByTheaters(
    val value: List<MovieScreeningInfoByTheater>,
) : Serializable
