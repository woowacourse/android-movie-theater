package woowacourse.movie.data

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.dataSource.movieDataSources

class MovieRepositoryImpl : MovieRepository {
    private val movies: List<Movie> = mutableListOf<Movie>().apply {
        repeat(10) { addAll(movieDataSources.toList()) }
    }

    override fun allMovies(): List<Movie> = movies.toList()
}
