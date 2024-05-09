package woowacourse.movie.repository

import woowacourse.movie.model.Theater

interface TheaterRepository {
    fun theaterById(theaterId: Long): Theater?

    fun theatersByMovieId(movieId: Long): List<Theater>
}
