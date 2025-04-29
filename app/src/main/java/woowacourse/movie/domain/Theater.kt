package woowacourse.movie.domain

data class Theater(
    val name: String,
    val movies: Movies,
) {
    fun isShowing(movie: Movie): Boolean {
        return movies.toList().contains(movie)
    }
}
