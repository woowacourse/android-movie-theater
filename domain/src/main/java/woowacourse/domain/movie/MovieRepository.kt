package woowacourse.domain.movie

import woowacourse.domain.util.CgvResult

interface MovieRepository {
    fun getMovies(): List<Movie>

    fun getMovie(movieId: Long): CgvResult<Movie>

    fun addMovie(
        title: String,
        screeningPeriod: ScreeningPeriod,
        runningTime: Int,
        description: String,
    )
}
