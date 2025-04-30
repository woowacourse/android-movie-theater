package woowacourse.movie.domain

import java.io.Serializable

data class Theater(
    val name: String,
    val schedule: Schedule,
) : Serializable
