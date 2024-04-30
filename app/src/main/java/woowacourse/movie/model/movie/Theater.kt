package woowacourse.movie.model.movie

data class Theater(
    val name: String,
    val movies: Movies,
    val id: Long = 0,
)
