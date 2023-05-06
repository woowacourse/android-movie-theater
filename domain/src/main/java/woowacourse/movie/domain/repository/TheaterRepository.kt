package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Theater

interface TheaterRepository {
    fun findTheaterByMovieId(movieId: Int): List<Theater>
}
