package woowacourse.movie.model.movie

data class Theater(
    val name: String,
    val movies: List<ScreeningMovie>,
    val id: Long = 0,
)
