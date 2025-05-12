package woowacourse.movie.data.repository

import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.Movie

interface MovieRepository {
    fun fetch(): List<Movie>
}

class LocalMovieRepository : MovieRepository {
    override fun fetch(): List<Movie> = MovieData.movies
}
