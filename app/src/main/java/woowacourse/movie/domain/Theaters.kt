package woowacourse.movie.domain

class Theaters(
    val values: List<Theater>,
) {
    fun filterByMovie(movie: Movie): List<Theater> {
        return values.filter { theater -> theater.isShowing(movie) }
    }
}
