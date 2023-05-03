package woowacourse.movie.data.dataSource

import woowacourse.movie.domain.Movie

class MovieDataSource : DataSource<Movie> {
    private val data: MutableList<Movie> =
        LocalDatabase.movieDBHelper?.selectAllMovies()?.toMutableList() ?: mutableListOf()
    override val value: List<Movie>
        get() = data

    override fun add(t: Movie) {
        data.add(t)
        LocalDatabase.movieDBHelper?.insertMovie(LocalDatabase.movieDBHelper?.writableDatabase, t)
    }
}
