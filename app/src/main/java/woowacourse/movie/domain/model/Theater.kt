package woowacourse.movie.domain.model

data class Theater(
    val name: String,
    val screenings: Set<ScreeningInfo>,
)
