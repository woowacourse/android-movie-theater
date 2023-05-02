package woowacourse.movie.domain.model

data class Movie(
    val poster: Image,
    val title: String,
    val date: DateRange,
    val runningTime: Int,
    val description: String,
)
