package woowacourse.data.movie

interface MovieDataSource {
    fun getMovieEntities(): List<MovieEntity>
    fun getMovieEntity(movieId: Long): MovieEntity?
}
