package woowacourse.movie.domain

data class Screening(
    val movieId: Int,
    val timeslot: List<Int>
)
