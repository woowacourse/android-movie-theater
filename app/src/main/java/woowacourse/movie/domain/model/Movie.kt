package woowacourse.movie.domain.model

data class Movie(
    val title: String,
    val poster: Int,
    val date: MovieDate,
    val runningTime: Int,
)
