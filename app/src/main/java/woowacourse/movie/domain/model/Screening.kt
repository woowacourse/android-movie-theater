package woowacourse.movie.domain.model

data class Screening(
    val movie: Movie,
    val theaterName: String,
    val times: List<MovieTime>,
)
