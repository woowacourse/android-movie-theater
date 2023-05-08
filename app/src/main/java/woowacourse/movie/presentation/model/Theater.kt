package woowacourse.movie.presentation.model

import woowacourse.movie.data.model.TheaterEntity

data class Theater(
    val name: String,
    val movies: List<Movie>
) {
    companion object {
        fun from(theaterEntity: TheaterEntity): Theater {
            return Theater(theaterEntity.name, theaterEntity.movies.map { Movie.from(it) })
        }
    }
}