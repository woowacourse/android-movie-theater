package woowacourse.movie.domain.movie

data class Movie(
    val title: String,
    val screeningDateTimes: ScreeningDateTimes,
    val runningTime: Minute,
    val summary: String
)
