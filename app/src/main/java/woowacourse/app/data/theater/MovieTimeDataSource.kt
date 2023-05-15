package woowacourse.app.data.theater

interface MovieTimeDataSource {
    fun getMovieTimeEntities(theaterId: Long): List<MovieTimeEntity>
    fun getMovieTimeEntity(theaterId: Long, movieId: Long): MovieTimeEntity?
    fun addMovieTimeEntity(
        theaterId: Long,
        movieId: Long,
        times: String,
    )
}
