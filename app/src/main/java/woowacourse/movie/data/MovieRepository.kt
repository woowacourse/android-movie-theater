package woowacourse.movie.data

import com.example.domain.model.Movie

object MovieRepository {

    private val movies: List<Movie> = mutableListOf<Movie>().apply {
        repeat(10) { addAll(movieDataSources.toList()) }
    }

    fun allMovies(): List<Movie> = movies.toList()
}
