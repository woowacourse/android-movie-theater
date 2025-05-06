package woowacourse.movie.domain.model

data class Screening(
    val movie: Movie,
    val times: List<Int>,
)
