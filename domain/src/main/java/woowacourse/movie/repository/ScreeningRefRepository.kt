package woowacourse.movie.repository

import woowacourse.movie.model.ScreeningRef

interface ScreeningRefRepository {
    fun screeningRefById(id: Long): ScreeningRef?

    fun screeningRefsByMovieId(movieId: Long): List<ScreeningRef>

    fun screeningRefsByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<ScreeningRef>
}
