package woowacourse.app.data.movie

import java.time.LocalDate

interface MovieDataSource {
    fun getMovieEntities(): List<MovieEntity>
    fun getMovieEntity(movieId: Long): MovieEntity?
    fun addMovieEntity(
        title: String,
        startDate: LocalDate,
        endDate: LocalDate,
        runningTime: Int,
        description: String,
    ): MovieEntity
}
