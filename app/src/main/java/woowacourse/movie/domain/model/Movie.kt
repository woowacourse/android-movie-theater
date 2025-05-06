package woowacourse.movie.domain.model

data class Movie(
    override val id: Long = 0,
    val title: String,
    val startDate: MovieDate,
    val endDate: MovieDate,
    val runningTime: Int,
) : Content(id)
