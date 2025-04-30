package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.movie.Movie

class ScreeningInfos(private val value: List<ScreeningInfo>) {
    fun findByMovie(movie: Movie): List<ScreeningInfo> = value.filter { screeningInfo ->
        screeningInfo.movie == movie
    }
}