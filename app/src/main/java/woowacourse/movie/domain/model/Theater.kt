package woowacourse.movie.domain.model

data class Theater(
    val id: Int,
    val name: String,
    var movieId: List<Int> = DEFAULT_MOVIE_ID,
) {
    companion object {
        private val DEFAULT_MOVIE_ID: List<Int> = emptyList()
    }
}
