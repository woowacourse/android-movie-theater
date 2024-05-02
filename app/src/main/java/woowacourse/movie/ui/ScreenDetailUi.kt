package woowacourse.movie.ui

import woowacourse.movie.domain.model.DateRange

data class ScreenDetailUi(
    val id: Int,
    val movieDetailUI: MovieDetailUI,
    val dateRange: DateRange,
)
