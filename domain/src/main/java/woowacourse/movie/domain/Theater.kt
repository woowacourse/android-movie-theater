package woowacourse.movie.domain

data class Theater(
    val name: String,
    val movieSchedules: List<MovieSchedule>
)
