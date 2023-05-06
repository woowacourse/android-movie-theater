package woowacourse.movie.domain

data class Theater(
    val name: String,
    val screenings: List<Screening>
)
