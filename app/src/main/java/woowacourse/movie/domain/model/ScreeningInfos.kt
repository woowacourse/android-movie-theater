package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.movie.Movie

class ScreeningInfos(
    private val value: List<Screening>,
) {
    fun findByMovie(movie: Movie): List<Screening> = value.filter { it.movie == movie }
}
