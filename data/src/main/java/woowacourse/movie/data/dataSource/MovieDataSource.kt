package woowacourse.movie.data.dataSource

import woowacourse.movie.domain.Movie

class MovieDataSource : DataSource<Movie> {
    private val data: MutableList<Movie> =
        LocalDatabase.movieDao?.selectAllMovies()?.toMutableList() ?: mutableListOf()
    override val value: List<Movie>
        get() = data

    override fun add(t: Movie) {
        data.add(t)
        LocalDatabase.movieDao?.insertMovie(LocalDatabase.movieDao?.writableDatabase, t)
    }
}
